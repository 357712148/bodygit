package com.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author liwen
 * @Title: WebSocketConfig
 * @Description: 开启WebSocket支持
 * @date 2019/12/9 / 13:28
 */
@Configuration
public class WebSocketConfig   {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
