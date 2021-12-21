package com.controller;

import com.common.Constant;
import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;
import com.entity.User;
import com.service.OrderService;
import com.service.ProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    ProductAdminService productAdminService;

    @Autowired
    OrderService orderService;

    @PostMapping("/toOrder")
    public ModelAndView orderPage(@RequestParam("phone") String phone,
                                  @RequestParam("saleNum") int[] saleNum, @RequestParam("address") String address,
                                  HttpSession session) {
        User user = (User) session.getAttribute(Constant.MALL_USER);
        int userId = user.getId();
        List<Product> list = (List<Product>) session.getAttribute("cart");
        int i = 0;
        for (Product aa : list) {
            int count = saleNum[i++];
            int id = aa.getId();
            Product product = productAdminService.selectById(id);
            SimpleDateFormat sdf = new SimpleDateFormat();
            String on = sdf.format(new Date());
            String orderNo = "jingxi" + on;
            Order order = new Order();
            order.setOrderNo(orderNo);
            order.setUserId(userId);
            order.setCreateTime(new Date());
            order.setOrderStatus(10);
            order.setReceiverMobile(phone);
            order.setReceiverAddress(address);
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setCount(count);
            orderDetail.setProductId(product.getId());
            orderDetail.setProductPrice(product.getPrice());
            orderDetail.setShopId(product.getShopId());
            orderDetail.setOrderNo(orderNo);
            orderService.add(order, orderDetail);
        }
        ModelAndView modelAndView = new ModelAndView("/order");
        return modelAndView;
    }
}
