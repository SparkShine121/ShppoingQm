package com.service.impl;

import com.controller.MiaoShaController;
import com.entity.MiaoshaOrder;
import com.entity.MiaoshaProduct;
import com.entity.Product;
import com.mapper.MiaoshaOrderMapper;
import com.mapper.MiaoshaProductMapper;
import com.redis.MiaoshaKey;
import com.redis.OrderKey;
import com.redis.RedisService;
import com.service.MiaoshaService;
import com.service.ProductAdminService;
import com.vo.MiaoshaOrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    RedisService redisService;
    @Autowired
    MiaoshaProductMapper miaoshaProductMapper;
    @Autowired
    MiaoshaOrderMapper miaoshaOrderMapper;
    @Autowired
    ProductAdminService productAdminService;
    @Autowired
    MiaoshaService miaoshaService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public MiaoshaProduct selectDetailById(int id) {
        MiaoshaProduct miaoshaProduct = miaoshaProductMapper.selectDetailById(id);
        Date date = miaoshaProduct.getStartTime();
        return miaoshaProduct;
    }

    @Override
    public MiaoshaOrder find(MiaoshaOrder miaoshaOrder) {
        return miaoshaOrderMapper.selectByUserIdAndProductId(miaoshaOrder);
    }

    @Override
    public MiaoshaOrderDetail findDetail(MiaoshaOrder miaoshaOrder) {
        MiaoshaOrder miaoshaOrder1 = miaoshaOrderMapper.selectByUserIdAndOrderId(miaoshaOrder);
        MiaoshaOrderDetail orderDetail = new MiaoshaOrderDetail();
        modelMapper.map(miaoshaOrder1, orderDetail);
        MiaoshaProduct miaoshaProduct = miaoshaProductMapper.selectDetailById(miaoshaOrder1.getProductId());
        orderDetail.setProductName(miaoshaProduct.getProductName());
        orderDetail.setProductImage(miaoshaProduct.getProductImage());
        return orderDetail;
    }

    @Override
    public void add(MiaoshaOrder miaoshaOrder, MiaoshaProduct miaoshaProduct) {

        miaoshaOrder.setProductId(miaoshaProduct.getId());
        miaoshaOrder.setMiaoshaprice(miaoshaProduct.getMiaoshaPrice());
        miaoshaOrder.setCreateTime(new Date());
        miaoshaOrder.setStatus(0);
        miaoshaOrderMapper.insertMiaoshaOrder(miaoshaOrder);
        Product product = new Product();
        product.setId(miaoshaOrder.getProductId());
        product.setStock(miaoshaProduct.getStock() - 1);
        productAdminService.updateStock(product);
        redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + miaoshaOrder.getUserId() + "_" + miaoshaOrder.getProductId(), miaoshaOrder);

    }

    @Override
    public List<MiaoshaProduct> findAll() {
        return miaoshaProductMapper.selectList();
    }

    @Override
    public List<MiaoshaOrder> getAllMiaoshaOrdersByGoodsId(int productId) {
        return miaoshaOrderMapper.selectAllByProductId(productId);
    }


    @Override
    public int getMiaoshaResult(int userId, int goodsId) {
        if (MiaoShaController.isGlobalActivityOver()) {
            return -1;
        }
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setUserId(userId);
        miaoshaOrder.setProductId(goodsId);
        MiaoshaOrder order = miaoshaService.find(miaoshaOrder);
        if (order != null) {//????????????
            return order.getId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (!isOver) {//????????????????????????????????????????????????
                return 0;
            } else {//??????????????????????????????????????????????????????????????????
                //???????????????????????????, ????????????????????????????????????????????????
                List<MiaoshaOrder> orders = miaoshaService.getAllMiaoshaOrdersByGoodsId(goodsId);
                if (orders == null || orders.size() < MiaoShaController.getGoodsStockOriginal(goodsId)) {
                    return 0;//?????????????????????
                } else {//?????????????????????????????????
                    MiaoshaOrder o = get(orders, userId);
                    if (o != null) {//?????????????????????????????????
                        return o.getId();
                    } else {//????????????
                        return -1;
                    }
                }
            }
        }
    }

    private MiaoshaOrder get(List<MiaoshaOrder> orders, int userId) {
        if (orders == null || orders.size() <= 0) {
            return null;
        }
        for (MiaoshaOrder order : orders) {
            if (order.getUserId() == (userId)) {
                return order;
            }
        }
        return null;
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(int goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }

}
