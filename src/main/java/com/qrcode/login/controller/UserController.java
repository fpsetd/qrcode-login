package com.qrcode.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.qrcode.login.entity.User;
import com.qrcode.login.service.UserService;
import com.qrcode.login.websocket.handler.QRCodeLoginHandler;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/json/find/{id}")
	public User findById(@PathVariable Integer id) {
		return userService.findById(id);
	}

	@GetMapping("/login")
	public ModelAndView toLogin(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		if (request.getHeader("user-agent").contains("Mobile")) {
			model.setViewName("m_login");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@GetMapping("/avatar/{account}")
	public String getAvatar(@PathVariable String account) {
		return userService.getAvatar(account);
	}

	@PostMapping("/login")
	public Map<String, Object> login(String account, String password, HttpServletRequest request) {
		Integer userId = userService.login(account, password);
		Map<String, Object> res = new HashMap<>();
		if (userId != null) {
			request.getSession().setAttribute("userId", userId);
			res.put("success", true);
		} else {
			res.put("success", false);
			res.put("code", "1001"); // 错误码
		}
		return res;
	}

	@GetMapping("/home")
	public ModelAndView homePage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if (userId == null) {
			model.setViewName("redirect:/user/login");
		} else {
			User user = userService.findById(userId);
			model.addObject("user", user);
			if (request.getHeader("user-agent").contains("Mobile")) {
				model.setViewName("m_home");
			} else {
				model.setViewName("home");
			}
		}
		return model;
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		ModelAndView model = new ModelAndView("redirect:/user/login");
		return model;
	}

	@GetMapping("/qrlogin/authorize")
	public ModelAndView qrcodeLogin(@RequestParam String sid, HttpServletRequest request) throws IOException {
		ModelAndView model = new ModelAndView("m_authorize");
		request.getSession().setAttribute("sessionId", sid);
		WebSocketSession socketSession = QRCodeLoginHandler.getSession(sid);
		socketSession.sendMessage(new TextMessage("{\"action\": \"scanCode\"}"));
		model.addObject("ipAddr", socketSession.getAttributes().get("ipAddr"));
		return model;
	}

	@PostMapping("/qrlogin/authorize")
	public Map<String, Object> authorize(boolean agree, HttpServletRequest request) throws IOException {
		Map<String, Object> res = new HashMap<>();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if (userId != null) {
			res.put("success", true);
			String sessionId = (String) request.getSession().getAttribute("sessionId");
			WebSocketSession socketSession = QRCodeLoginHandler.getSession(sessionId);
			if (agree) {
				HttpSession httpSession = (HttpSession) socketSession.getAttributes().get("httpSession");
				httpSession.setAttribute("userId", userId);
				socketSession.sendMessage(new TextMessage("{\"action\": \"agree\"}"));
			} else {
				socketSession.sendMessage(new TextMessage("{\"action\": \"refuse\"}"));
			}
			request.getSession().removeAttribute("sessionId");
			socketSession.close();
		} else {
			res.put("success", false);
			res.put("code", "1002");
		}
		return res;
	}
}
