package com.qrcode.login.websocket.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QRCodeLoginHandler implements WebSocketHandler {

	private static final Map<String, WebSocketSession> container = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		container.put(session.getId(), session);
		// 之前的sid是一个以16进制递增的数字字符串，为消除连续性，新版Spring将sid改为了uuid形式
		session.sendMessage(new TextMessage(String.format("{\"action\": \"connect\", \"sid\": \"%s\"}", session.getId())));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println(message.getPayload());
		session.sendMessage(new TextMessage("高贵的服务器不想理你"));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (!session.isOpen()) {
			container.remove(session.getId());
		}
		System.out.println(exception.getMessage());
		throw new Exception(exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		container.remove(session.getId());
		System.out.println("一个连接关闭了：" + session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	public static WebSocketSession getSession(String sessionId) {
		return container.get(sessionId);
	}
}
