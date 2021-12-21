package com.mapper;

import com.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductMapper {
    Product selectProduct(int productId);

    void insertProduct(Product product);

    /**
     * 查询同一家店铺有没有同名的商品
     */
    Product selectTongMing(Product product);

    List<Product> selectListForAdmin(Product product);

    void update(Product product);

    Product selectById(int id);

    /**
     * 修改商品库存
     */
    void updateStock(Product product);
}
