package com.qrcode.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class SpringWebSocketConfigurator implements WebSocketConfigurer {

	@Autowired
	private HandshakeInterceptor bindHttpSessionInterceptor;
	@Autowired
	private WebSocketHandler qrcodeLoginHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(qrcodeLoginHandler, "/websocket/login").addInterceptors(bindHttpSessionInterceptor);
	}
}
