package com.entity;

import java.util.Date;

public class Cart {
    private int id;
    private int shopId;
    private int quantity;
    private int selected;
    private int productId;
    private Date createTime;
    private int userId;

    public Cart() {

    }

    public Cart(int id, int shopId, int quantity, int selected, Date createTime, int userId, int productId) {
        this.id = id;
        this.shopId = shopId;
        this.quantity = quantity;
        this.selected = selected;
        this.createTime = createTime;
        this.userId = userId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
