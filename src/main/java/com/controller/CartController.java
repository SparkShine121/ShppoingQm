package com.controller;

import com.entity.Product;
import com.service.CartService;
import com.service.ProductAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
//@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductAdminService productAdminService;

    @GetMapping("/list")
    public ModelAndView cartList(HttpSession session) {
        List<Product> cartList = (List<Product>) session.getAttribute("cart");
        ModelAndView mav = new ModelAndView("/cartList");
        mav.addObject("cartList", cartList);
        return mav;
    }

    @GetMapping("/toCart/{id}")
    public ModelAndView toCart(@PathVariable("id") int id, HttpSession session) {
        Product product = productAdminService.selectById(id);
//        System.out.println(product+"sssssssssssssssssssssssssss");
        List<Product> cart;
        if (session.getAttribute("cart") == null) {
            cart = new ArrayList<>();
            cart.add(product);
            log.info("购物车新增数据为：{}", product);
            session.setAttribute("cart", cart);
        } else {
            cart = (List<Product>) session.getAttribute("cart");
            boolean flag = true;
            for (Product tt : cart) {
                if (tt.getId() == product.getId()) {
                    flag = false;
                }
            }
            if (flag) {
                cart.add(product);
            }
            log.info("购物车新增数据为：{}", product);
        }
        log.info("session中新增数据{}", product);
        ModelAndView mav = new ModelAndView("/cartList");
        mav.addObject("cartList", cart);
        return mav;
    }
}
