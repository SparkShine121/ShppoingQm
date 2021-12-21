package com.mapper;

import com.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//使用xml来写sql,这里的注解得是Mapper,不能是Repository
@Mapper
public interface UserMapper {

    /**
     * 查询用户名是否存在
     */
    User selectName(User user);

    /**
     * 查询该登录用户是否存在
     */
    User login(User user);

    /**
     * 注册用户
     */
    void register(User user);

    /**
     * 查询平台管理员
     */
    List<User> selectPt();

    User selectById(User user);
}
