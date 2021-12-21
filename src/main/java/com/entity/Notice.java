package com.entity;

import java.util.Date;

public class Notice {

    private int id;
    private int userId;
    private String content;
    private Date createTime;
    private int orderNo;
    private int sqrId;

    public Notice() {

    }

    public Notice(int id, int userId, String content, Date createTime, int orderNo, int sqrId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createTime = createTime;
        this.orderNo = orderNo;
        this.sqrId = sqrId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getSqrId() {
        return sqrId;
    }

    public void setSqrId(int sqrId) {
        this.sqrId = sqrId;
    }
}
