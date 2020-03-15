package com.qrcode.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;

@Configuration
@EnableWebSocket
public class SpringWebSocketConfigurator implements WebSocketConfigurer {

    @Resource
    private HandshakeInterceptor bindHttpSessionInterceptor;
    @Resource
    private WebSocketHandler qrCodeLoginHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(qrCodeLoginHandler, "/websocket/login").addInterceptors(bindHttpSessionInterceptor);
    }
}
