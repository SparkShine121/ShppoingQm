package com.entity;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String password;
    private int status;
    private int hbStatus;
    private int salt;
    private Date date;
    private int auth;

    public User() {

    }

    public User(int id, String username, String password, int status, int hbStatus, int salt, Date date, int auth) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.hbStatus = hbStatus;
        this.salt = salt;
        this.date = date;
        this.auth = auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHbStatus() {
        return hbStatus;
    }

    public void setHbStatus(int hbStatus) {
        this.hbStatus = hbStatus;
    }

    public int getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }
}
