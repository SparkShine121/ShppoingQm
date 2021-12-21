package com.entity;

import java.util.Date;

public class Product {
    private int id;
    private String productName;
    private String productImage;
    private int status;
    private double price;
    private String detail;
    private int shopId;
    private int stock;
    private Date updateTime;

    public Product() {

    }

    public Product(int id, Date updateTime, String productName, String productImage, int status, double price, String detail, int shopId, int stock) {
        this.id = id;
        this.productName = productName;
        this.productImage = productImage;
        this.status = status;
        this.price = price;
        this.detail = detail;
        this.shopId = shopId;
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", detail='" + detail + '\'' +
                ", shopId=" + shopId +
                ", stock=" + stock +
                ", updateTime=" + updateTime +
                '}';
    }
}
