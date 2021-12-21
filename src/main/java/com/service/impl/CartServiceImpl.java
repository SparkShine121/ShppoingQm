package com.service.impl;

import com.common.Constant;
import com.entity.Cart;
import com.entity.Product;
import com.exception.ImoocMallException;
import com.exception.ImoocMallExceptionEnum;
import com.mapper.CartMapper;
import com.mapper.ProductMapper;
import com.service.CartService;
import com.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<CartVO> list(int userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
//        for (int i = 0; i < cartVOS.size(); i++) {
//            CartVO cartVO = cartVOS.get(i);
//            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
//        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(int userId, int productId, int count, int shopId) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectCart(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setShopId(shopId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setCreateTime(new Date());
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertCart(cart);
        } else {
            //这个商品已经在购物车里了，则数量相加
            count = cart.getQuantity() + 1;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateCart(cartNew);
        }
        return this.list(userId);
    }

    /**
     * 校验商品库存是否足够
     */
    //TODO
    private void validProduct(int productId, int count) {
        Product product = productMapper.selectProduct(productId);
        if (count > product.getStock()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(int userId, int productId, int count, int shopId) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectCart(userId, productId);
        //这个商品已经在购物车里了，则更新数量
        Cart cartNew = new Cart();
        cartNew.setQuantity(count);
        cartNew.setId(cart.getId());
        cartNew.setProductId(cart.getProductId());
        cartNew.setUserId(cart.getUserId());
        cartNew.setSelected(Constant.Cart.CHECKED);
        cartMapper.updateCart(cartNew);
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(int userId, int productId, int shopId) {
        Cart cart = new Cart();
        cart.setShopId(shopId);
        cart.setProductId(productId);
        cart.setUserId(userId);
        cartMapper.deleteCart(cart);
        return this.list(userId);
    }

    //全选中或全不选
    @Override
    public List<CartVO> selectOrNot(int userId, int productId, int selected, int shopId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setSelected(selected);
        cart.setShopId(shopId);
        cartMapper.selectOrNot(cart);
        return this.list(userId);
    }
}
