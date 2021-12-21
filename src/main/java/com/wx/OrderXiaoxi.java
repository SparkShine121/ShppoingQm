package com.wx;

import com.common.Constant;
import com.entity.User;
import com.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/orderChat", configurator = GetHttpSessionOrderConfigurator.class)
@Component
public class OrderXiaoxi {

    //一个用户对应一个EndPoint对象
    //用线程安全的map来保存当前用户(一定要用static,这样在不同的线程中得到的才是同样的map集合)
    //所有用户共用一个map
    //使用这个是为了并发安全,ConcurrentHashMap
    private static Map<String, OrderXiaoxi> onLineOrder = new ConcurrentHashMap<>();
    //声明一个session对象，通过该对象可以发送消息给指定用户，不能设置为静态，每个ChatEndPoint有一个session才能区分.(websocket的session)
    private Session session;
    //保存当前登录浏览器的用户
    private HttpSession httpSession;

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
        onLineOrder.put(username, this);
    }

    //获取当前登录的所有用户
    private Set<String> getNames() {
        return onLineOrder.keySet();

    }

    //用户之间的信息发送
    @OnMessage
    public void onMessage(String message, Session session) {

    }

    public void sendMessage(String message, String toName) {
        try {
            if (StringUtils.hasLength(toName)) {
                onLineOrder.get(toName).session.getBasicRemote().sendText(message);
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
            onLineOrder.remove(username);
        }
        httpSession.removeAttribute(username);
        String message = MessageUtils.getMessage(true, null, getNames());
    }
}
