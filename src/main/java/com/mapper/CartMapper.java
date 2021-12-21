package com.mapper;

import com.entity.Cart;
import com.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    List<CartVO> selectList(@Param("userId") int userId);

    Cart selectCart(@Param("userId") int userId, @Param("productId") int productId);

    void insertCart(Cart cart);

    void updateCart(Cart cart);

    void deleteCart(Cart cart);

    void selectOrNot(Cart cart);
}
