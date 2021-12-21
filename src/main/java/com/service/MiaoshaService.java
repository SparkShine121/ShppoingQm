package com.service;

import com.entity.MiaoshaOrder;
import com.entity.MiaoshaProduct;
import com.vo.MiaoshaOrderDetail;

import java.util.List;

public interface MiaoshaService {

    MiaoshaProduct selectDetailById(int id);

    /**
     * 查询秒杀订单是否存在
     */
    MiaoshaOrder find(MiaoshaOrder miaoshaOrder);

    //查找订单详情
    MiaoshaOrderDetail findDetail(MiaoshaOrder miaoshaOrder);

    //写入秒杀订单
    void add(MiaoshaOrder miaoshaOrder, MiaoshaProduct miaoshaProduct);

    /**
     * 查询全部的秒杀商品
     */
    List<MiaoshaProduct> findAll();

    List<MiaoshaOrder> getAllMiaoshaOrdersByGoodsId(int productId);

    int getMiaoshaResult(int userId, int goodsId);
}
