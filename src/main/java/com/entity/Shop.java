package com.entity;


import java.util.Date;

public class Shop {
    private int id;
    private String shopName;
    private String shopAddr;
    private String shopDetail;
    private Date createDate;
    private int status;
    private int business;
    private double monthSales;
    private String shopType;
    private String shopPhone;
    private String shopImg;
    private int userId;

    public Shop() {

    }

    public Shop(int id, String shopName, String shopAddr, String shopDetail, Date createDate,
                int status, String shopImg, int business, double monthSales, String shopType, String shopPhone, int userId) {
        this.id = id;
        this.shopName = shopName;
        this.shopAddr = shopAddr;
        this.shopDetail = shopDetail;
        this.createDate = createDate;
        this.status = status;
        this.business = business;
        this.monthSales = monthSales;
        this.shopType = shopType;
        this.shopPhone = shopPhone;
        this.userId = userId;
        this.shopImg = shopImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getShopDetail() {
        return shopDetail;
    }

    public void setShopDetail(String shopDetail) {
        this.shopDetail = shopDetail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public double getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(double monthSales) {
        this.monthSales = monthSales;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }
}
