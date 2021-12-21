package com.service.impl;

import com.entity.*;
import com.exception.ImoocMallException;
import com.exception.ImoocMallExceptionEnum;
import com.mapper.OrderMapper;
import com.service.OrderService;
import com.service.ProductAdminService;
import com.service.UserService;
import com.wx.OrderXiaoxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderXiaoxi orderXiaoxi;
    @Autowired
    UserService userService;

    @Autowired
    ProductAdminService productAdminService;

    @Transactional
    @Override
    public void add(Order order, OrderDetail orderDetail) {
        System.out.println(order);
        Product product = productAdminService.selectById(orderDetail.getProductId());
        int id = product.getShopId();
        Shop shop = productAdminService.selectByShopId(id);
        int userId = shop.getUserId();
        User user = new User();
        user.setId(userId);
        User user1 = userService.selectById(user);
        String toName = user1.getUsername();
        double price = product.getPrice();
        int count = orderDetail.getCount();
        if (product.getStock() < count) {
            orderXiaoxi.sendMessage(product.getProductName() + "商品库存不足", toName);
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
        order.setTotalPrice(price * count);
        int stockNew = product.getStock() - count;
        product.setStock(stockNew);
        productAdminService.updateStock(product);
        orderMapper.insert(order);
        orderMapper.insertDetail(orderDetail);
        orderXiaoxi.sendMessage("你有一个新的订单", toName);
    }

    @Override
    public void testAdd(Order order) {
        orderMapper.insert(order);
    }
}
