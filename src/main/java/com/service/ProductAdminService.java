package com.service;

import com.entity.Notice;
import com.entity.Product;
import com.entity.Shop;
import com.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductAdminService {

    /**
     * 商家申请入驻
     */
    void shengqing(Shop shop, User user);

    /**
     * 显示通知消息
     */
    List<Notice> selectNotice(User user);

    /**
     * 获取申请信息
     **/
    List<Shop> selectShop();

    void tongguo(Shop shop, User user);

    void butongguo(Shop shop, User user);

    void insertProduct(Product product);

    /**
     * 通过用户ID去查询他注册的商铺
     */
    Shop selectByID(User user);

    /**
     * 通过商品ID查询商品列表
     */
    List<Product> selectList(Product product);

    PageInfo listForAdmin(Integer pageNum, Integer pageSizem, Product product);

    void update(Product product);

    /**
     * 根据ID查询商品信息
     */
    Product selectById(int id);

    /**
     * 修改商品库存
     */
    void updateStock(Product product);

    Shop selectByShopId(int id);
}
