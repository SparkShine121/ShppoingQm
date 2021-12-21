package com.mapper;

import com.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {
    //新增商铺
    void insertShop(Shop shop);

    //判断是否有重名的商铺
    Shop selectName(Shop shop);

    /**
     * 平台获取申请信息
     */
    List<Shop> selectShop();

    /**
     * 审批通过
     */
    void update(Shop shop);

    /**
     * 通过用户ID去查询他注册的商铺
     */
    List<Shop> selectByID(Shop shop);

    Shop selectByShopId(int id);
}
