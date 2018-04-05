package com.qrcode.login.websocket.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * websocket 通过普通 http 请求握手建立连接，
 * 此处拦截握手请求，获取请求扫码登录客户端的 HttpSession,
 * 并将 HttpSession 绑定到 WebSocketSession 的 attribute 中
 * @author bogon
 *
 */
@Component
public class BindHttpSessionInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			HttpSession httpSession = ((ServletServerHttpRequest) request).getServletRequest().getSession();
			attributes.put("httpSession", httpSession);
			attributes.put("ipAddr", request.getRemoteAddress().getHostName());
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		
	}
}
