package com.wx;

import com.chatpojo.Message;
import com.common.Constant;
import com.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filter.UserChatInterceptor;
import com.service.WeChatService;
import com.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndPoint {

    //@Autowired
    private static WeChatService weChatService;
    //一个用户对应一个EndPoint对象
    //用线程安全的map来保存当前用户(一定要用static,这样在不同的线程中得到的才是同样的map集合)
    //所有用户共用一个map
    //使用这个是为了并发安全,ConcurrentHashMap
    private static Map<String, ChatEndPoint> onLineUsers = new ConcurrentHashMap<>();
    //声明一个session对象，通过该对象可以发送消息给指定用户，不能设置为静态，每个ChatEndPoint有一个session才能区分.(websocket的session)
    private Session session;
    //保存当前登录浏览器的用户
    private HttpSession httpSession;

    @Autowired
    public void setWeChatService(WeChatService weChatService) {
        ChatEndPoint.weChatService = weChatService;
    }

    //建立连接时发送系统广播
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //将局部的session对象赋值给成员session
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        User user = (User) httpSession.getAttribute(Constant.MALL_USER);
        String username = user.getUsername();
        log.info("上线用户名称：" + username);
        //this表示当前类对象
        onLineUsers.put(username, this);

        //将当前在线的用户名单推送给所有的客户端
        String message = MessageUtils.getMessage(true, null, getNames());
        broadcastAllUsers(message);
    }

    //获取当前登录的所有用户
    private Set<String> getNames() {
        return onLineUsers.keySet();
    }

    //发送系统消息
    private void broadcastAllUsers(String message) {
        try {
            Set<String> names = onLineUsers.keySet();
            for (String name : names) {
                ChatEndPoint chatEndPoint = onLineUsers.get(name);
                //获取每个用户的session
                chatEndPoint.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //用户之间的信息发送
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            String toName = mess.getToName();
            String data = mess.getMessage();
            User user = (User) httpSession.getAttribute(Constant.MALL_USER);
            String username = user.getUsername();
            log.info(username + "向" + toName + "发送的消息：" + data);

            Message message1 = new Message();
            message1.setMessage(data);
            message1.setFromName(username);
            message1.setToName(toName);
            message1.setDateTime(new Date());
            log.info("发送时间是:" + message1.getDateTime());
            //存储聊天记录
            try {
                weChatService.add(message1);
            } catch (Exception e) {
                log.info("新增异常");
            }
            log.info(username + "向" + toName + "发送的消息的聊天消息新增成功");
            String resultMessage = MessageUtils.getMessage(false, username, data);
            if (StringUtils.hasLength(toName)) {
                onLineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //用户断开连接的断后操作
    @OnClose
    public void onClose(Session session) {
        User user = (User) httpSession.getAttribute(Constant.MALL_USER);
        String username = user.getUsername();
        log.info("离线用户：" + username);
        if (username != null) {
            onLineUsers.remove(username);
            UserChatInterceptor.onLineUsers.remove(username);
        }
        httpSession.removeAttribute(username);
        String message = MessageUtils.getMessage(true, null, getNames());
        broadcastAllUsers(message);
    }
}
