package com.mapper;

import com.entity.Order;
import com.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    void insert(Order order);

    void insertDetail(OrderDetail orderDetail);
}
