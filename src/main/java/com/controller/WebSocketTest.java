package com.controller;

import com.wx.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSocketTest {

    @Autowired
    WebSocket webSocket;

    @GetMapping("/dingdang")
    public ModelAndView dingdang() {
        return new ModelAndView("/dingdang");
    }

    @GetMapping("/socketTest")
    public void socketTest() {
        webSocket.sendMessage("你有一个新的订单");
    }

}
