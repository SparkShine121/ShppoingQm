package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//页面跳转
public class PageChatController {

    @RequestMapping("/main")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("/main");
        return modelAndView;
    }

    @RequestMapping("/loginerror")
    public String longinError() {
        return "loginerror";
    }
}
