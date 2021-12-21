package com.service;

import com.entity.Order;
import com.entity.OrderDetail;

public interface OrderService {

    /**
     * 添加订单
     */
    void add(Order order, OrderDetail orderDetail);

    void testAdd(Order order);
}
