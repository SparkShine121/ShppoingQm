package com.controller;

import com.common.ApiRestResponse;
import com.common.Constant;
import com.entity.Notice;
import com.entity.Shop;
import com.entity.User;
import com.service.ProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/ptAdmin")
public class ProductPtAdminController {

    @Autowired
    ProductAdminService productAdminService;

    @RequestMapping("/ptindex")
    public ModelAndView hhh() {
        ModelAndView modelAndView = new ModelAndView("/ptindex");//设置对应JSP的模板文件
        //modelAndView.addObject("name", "miyue");
        return modelAndView;
    }

    @RequestMapping("/notice")
    public ModelAndView notice() {
        //进入页面，由页面主动请求获取数据
        return new ModelAndView("/ptNotice");
    }

    @GetMapping("/notice/list")
    @ResponseBody
    public ApiRestResponse noticeList(HttpSession session) {
        User user = (User) session.getAttribute(Constant.MALL_USER);
        List<Notice> noticeList = productAdminService.selectNotice(user);
        return ApiRestResponse.success(noticeList);
    }

    @GetMapping("/shop/list")
    public ModelAndView shopList() {
        List<Shop> shopList = productAdminService.selectShop();
        ModelAndView mav = new ModelAndView("/audit");
        mav.addObject("shopList", shopList);
        return mav;
    }

    @RequestMapping("/audit")
    public ModelAndView audit() {
        //进入页面，由页面主动请求获取数据
        return new ModelAndView("/audit");
    }

    @RequestMapping(value = "/audit/tongyi/{id}")
    public String tongguo(@PathVariable("id") int id, HttpSession session) {
        User user = (User) session.getAttribute(Constant.MALL_USER);
        Shop shop = new Shop();
        shop.setId(id);
        productAdminService.tongguo(shop, user);
        return "redirect:/ptAdmin/shop/list";

    }

    @RequestMapping(value = "/audit/butongyi/{id}")
    public String butongyi(@PathVariable("id") int id, HttpSession session) {
        User user = (User) session.getAttribute(Constant.MALL_USER);
        Shop shop = new Shop();
        shop.setId(id);
        productAdminService.butongguo(shop, user);
        return "redirect:/ptAdmin/shop/list";
    }

}
