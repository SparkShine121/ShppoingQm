package com.controller;

import com.common.ApiRestResponse;
import com.common.Constant;
import com.entity.MiaoshaOrder;
import com.entity.MiaoshaProduct;
import com.entity.User;
import com.exception.ImoocMallException;
import com.exception.ImoocMallExceptionEnum;
import com.rabbitmq.MQSender;
import com.rabbitmq.MiaoshaMessage;
import com.redis.GoodsKey;
import com.redis.RedisService;
import com.service.MiaoshaService;
import com.vo.MiaoshaOrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * 秒杀并发的瓶颈在数据库,应当减少对数据库的访问
 **/

/**
 * 使用页面静态化,将html页面缓存
 **/
//@Controller
@Slf4j
public class MiaoShaController implements InitializingBean {

    //
    private static volatile boolean isGlobalActivityOver = false;
    private static HashMap<Integer, Integer> stockMap = new HashMap<Integer, Integer>();
    @Autowired
    MiaoshaService miaoshaService;
    @Autowired
    RedisService redisService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    MQSender mqSender;
    private HashMap<Integer, Boolean> localOverMap = new HashMap<Integer, Boolean>();

    /**
     * 获取初始的商品秒杀数量
     */
    public static int getGoodsStockOriginal(int goodsId) {
        Integer stock = stockMap.get(goodsId);
        if (stock == null) {
            return 0;
        }
        return stock;
    }

    /**
     * 通过管理后台设置一个全局秒杀结束的标志，防止数据库、redis、rabbitmq等发生意外，活动无法结束
     **/
    public static void setGlobalActivityOver() {
        isGlobalActivityOver = true;
    }

    public static boolean isGlobalActivityOver() {
        return isGlobalActivityOver;
    }

    /**
     * 系统初始化
     * afterPropertiesSet是实现InitializingBean的方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<MiaoshaProduct> list = miaoshaService.findAll();
        if (list == null) {
            return;
        }
        for (MiaoshaProduct goods : list) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), goods.getStock());
            stockMap.put(goods.getId(), goods.getStock());
            localOverMap.put(goods.getId(), false);
        }
    }

    /**
     * 前端使用ajax轮训来获取秒杀结果
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping(value = "/miaosha/result", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse result(HttpSession session,
                                  @RequestParam("goodsId") int goodsId) {
        User user = (User) session.getAttribute(Constant.MALL_USER);
        int result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return ApiRestResponse.success(result);
    }

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse miaosha(@RequestParam("goodsId") int goodsId, HttpSession session) {
        User user = (User) session.getAttribute(Constant.MALL_USER);
        log.info("商品id:{}", goodsId);
        //判断秒杀是否结束的标志
        if (isGlobalActivityOver()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.MIAO_SHA_OVER);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            throw new ImoocMallException(ImoocMallExceptionEnum.MIAO_SHA_OVER);
        }
        //预减库存,为了减少对数据库的操作
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);//10
        if (stock < 0) {
            //使用内存标记,可以在秒杀商品数量不够时,连redis都无需访问就直接放回秒杀失败的消息
            localOverMap.put(goodsId, true);
            throw new ImoocMallException(ImoocMallExceptionEnum.MIAO_SHA_OVER);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        //发送秒杀订单消息
        mqSender.sendTopic(mm);
        return ApiRestResponse.success(goodsId);
    }

    @RequestMapping(value = "/to_detail2/{goodsId}")
    public ModelAndView detail(HttpSession session,
                               @PathVariable("goodsId") int goodsId) {

        ModelAndView model = new ModelAndView("/goods_detail");
        User user = (User) session.getAttribute(Constant.MALL_USER);
        model.addObject("user", user);

        MiaoshaProduct goods = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, MiaoshaProduct.class);
        System.out.println("从缓存中取到的数据" + goods);
        if (goods == null) {
            goods = miaoshaService.selectDetailById(goodsId);
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, goods);
        }
        model.addObject("goods", goods);
        long startAt = goods.getStartTime().getTime();
        long endAt = goods.getEndTime().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        log.info("剩余时间{}", remainSeconds);
        model.addObject("miaoshaStatus", miaoshaStatus);
        model.addObject("remainSeconds", remainSeconds);
        return model;
    }

    @RequestMapping("/miaosha_detail/{orderId}")
    @ResponseBody
    public ModelAndView detail(@PathVariable int orderId, HttpSession session) {
        ModelAndView mav = new ModelAndView("/miaosha_order_detail");
        User user = (User) session.getAttribute(Constant.MALL_USER);
        int userId = user.getId();
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setId(orderId);
        miaoshaOrder.setUserId(userId);
        MiaoshaOrderDetail miaoshaOrder1 = miaoshaService.findDetail(miaoshaOrder);
        mav.addObject("order", miaoshaOrder1);
        return mav;
    }
}
