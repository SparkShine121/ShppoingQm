package com.controller.test2;

import com.entity.Order;
import com.entity.OrderDetail;
import com.mapper.OrderMapper;
import com.rabbitmq.MQSender;
import com.service.OrderService;
import com.wx.OrderXiaoxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

//@Controller
public class TestOrder {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    MQSender mqSender;

    @GetMapping("/testMQ")
    public void testMQ() {
        mqSender.sendTopic("有订单生成");
    }

    @GetMapping("/hello2222")
    public ModelAndView hello2() {
        ModelAndView modelAndView = new ModelAndView("thyme");
        modelAndView.addObject("name", "张三");
        return modelAndView;
    }

    @GetMapping("/testorder")
    public void testOrder() {
        Order order = new Order();
        order.setUserId(26);
        order.setOrderNo("225");
        order.setTotalPrice(35.5);
        order.setReceiverMobile("2255533");
        order.setOrderStatus(15);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setReceiverAddress("集美");
        orderService.testAdd(order);
    }

    @GetMapping("/testDetail")
    public void testOrderDe() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId(5);
        orderDetail.setProductPrice(25.5);
        orderDetail.setShopId(2);
        orderDetail.setOrderNo("2555");
        orderDetail.setCount(5);
        orderMapper.insertDetail(orderDetail);
    }

    @GetMapping("/send11")
    public void sendMessage() {
        OrderXiaoxi orderXiaoxi = new OrderXiaoxi();
//        orderXiaoxi.sendMessage("zhengyi");
    }
}
