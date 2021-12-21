package com.mapper;

import com.entity.MiaoshaOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiaoshaOrderMapper {

    void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    void update(MiaoshaOrder miaoshaOrder);

    /**
     * 根据用户ID和商品ID查询订单消息
     */
    MiaoshaOrder selectByUserIdAndProductId(MiaoshaOrder miaoshaOrder);

    List<MiaoshaOrder> selectAllByProductId(int productId);

    /**
     * 根据用户ID和订单ID查询订单消息
     */
    MiaoshaOrder selectByUserIdAndOrderId(MiaoshaOrder miaoshaOrder);
}
