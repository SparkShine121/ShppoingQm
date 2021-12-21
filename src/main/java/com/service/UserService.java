package com.service;

import com.entity.User;

public interface UserService {

    /**
     * 注册用户
     */
    public User register(User user);

    /**
     * 登录
     */
    public User login(User user);

    boolean checkAdminRole(User currentUser);

    boolean checkPtAdminRole(User currentUser);

    User selectPt();

    User selectById(User user);
}
