package com.controller;

import com.common.ApiRestResponse;
import com.common.Constant;
import com.entity.User;
import com.exception.ImoocMallException;
import com.exception.ImoocMallExceptionEnum;
import com.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/templete")
    public ModelAndView tem() {
        return new ModelAndView("/testMt");
    }

    @GetMapping("/login")
    public ModelAndView showlogin() {

        return new ModelAndView("/login");
    }

    @PostMapping("/check_login")
    @ResponseBody
    public ApiRestResponse checkLogin(@RequestBody User user, HttpSession session) {

        User user1 = userService.login(user);
        user.setPassword(null);
        boolean flag = userService.checkAdminRole(user1);
        boolean flag1 = userService.checkPtAdminRole(user1);
        String url = null;
        if (flag) {
            url = "/admin/index";
        } else if (flag1) {
            url = "/ptAdmin/ptindex";
        } else {
            url = "/jsp/hello.jsp";
        }
        session.setAttribute(Constant.MALL_USER, user1);

        return ApiRestResponse.success(url);
    }


    @GetMapping("/showRegister")
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @PostMapping("/check_register")
    @ResponseBody
    public ApiRestResponse checkRegister(@Param("username") String username,
                                         @Param("password") String password, @Param("password2") String password2) {
        User user = new User();
        //密码长度不能少于5位
        if (password.length() < 5) {
            throw new ImoocMallException(ImoocMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        if (!password.equals(password2)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD_TWO);
        }
        user.setUsername(username);
        user.setPassword(password);
        userService.register(user);
        return ApiRestResponse.success();
    }
}
