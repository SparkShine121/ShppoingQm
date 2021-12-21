package com.controller;

import com.chatpojo.Message;
import com.common.ApiRestResponse;
import com.common.Constant;
import com.entity.User;
import com.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
//模拟登录操作
@Slf4j
public class CertificationChatController {

    @Autowired
    WeChatService weChatService;

    @RequestMapping("/getUsername")
    public String getUsername(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(Constant.MALL_USER);
        //String username = (String) httpSession.getAttribute("user");
        String username = user.getUsername();
        return username;
    }

    @GetMapping("/jilu")
    public ApiRestResponse getJiLu(@RequestParam("username") String username,
                                   @RequestParam("toName") String toName) {
        //TODO
        //toName未成功接收参数,但是前端的toName有数据
        log.info("用户名" + username + "接收方" + toName);
        List<Message> list = weChatService.findByName(username, toName);
        for (Message message : list) {
            log.info("聊天记录" + message.getMessage());
        }
        return ApiRestResponse.success(list);
    }
}
