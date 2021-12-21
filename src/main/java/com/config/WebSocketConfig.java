package com.config;

import com.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
//websocket要做的配置类
public class WebSocketConfig {

    private WeChatService weChatService;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    private void setWeChatService(WeChatService weChatService) {
        this.weChatService = weChatService;
    }
}
