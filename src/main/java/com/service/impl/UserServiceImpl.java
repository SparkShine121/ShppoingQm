package com.service.impl;

import com.entity.User;
import com.exception.ImoocMallException;
import com.exception.ImoocMallExceptionEnum;
import com.mapper.UserMapper;
import com.service.UserService;
import com.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User register(User user) {
        User user1 = userMapper.selectName(user);
        if (user1 != null) {
            //用户名已存在
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int salt = new Random().nextInt(1000) + 1000; //盐值
        String password1 = MD5Utils.md5Digest(user.getPassword(), salt);
        user.setSalt(salt);
        user.setHbStatus(0);
        user.setAuth(0);
        user.setPassword(password1);
        user.setDate(new Date());
        userMapper.register(user);
        return user;
    }

    @Override
    public User login(User user) {
        User user1 = userMapper.selectName(user);
        if (user1 == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_USERNAME);
        }
        String password1 = MD5Utils.md5Digest(user.getPassword(), user1.getSalt());
        user.setPassword(password1);
        User user2 = userMapper.login(user);
        if (user2 == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user1;
    }

    /**
     * 校验是否是管理员
     **/
    @Override
    public boolean checkAdminRole(User currentUser) {
        if (currentUser.getAuth() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 校验是否是平台管理员
     **/
    @Override
    public boolean checkPtAdminRole(User currentUser) {
        if (currentUser.getAuth() == 2) {
            return true;
        }
        return false;
    }

    /**
     * 查询平台管理员
     */
    @Override
    public User selectPt() {
        List<User> list = userMapper.selectPt();
        return list.get(0);
    }

    /**
     * 根据Id查询用户信息
     */
    @Override
    public User selectById(User user) {
        User user1 = userMapper.selectById(user);
        return user1;
    }

}
