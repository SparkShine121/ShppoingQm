package com.service;

import com.vo.CartVO;

import java.util.List;

/**
 * 购物车service
 */
public interface CartService {

    List<CartVO> list(int userId);

    List<CartVO> add(int userId, int productId, int shopId, int count);

    List<CartVO> update(int userId, int productId, int count, int shopId);

    List<CartVO> delete(int userId, int productId, int shopId);

    List<CartVO> selectOrNot(int userId, int productId, int selected, int shopId);


}
