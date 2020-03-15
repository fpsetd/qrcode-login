package com.qrcode.login.service;

import com.qrcode.login.entity.User;
import com.qrcode.login.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserService {

	@Resource
	private UserRepository userRepository;

	public User findById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public String getAvatar(String account) {
		User user = userRepository.findByAccount(account);
		return user != null ? user.getAvatar() : "";
	}

	public Integer login(String account, String password) {
		User user = userRepository.findByAccount(account);
		return (user != null && user.getPassword().equals(password)) ? user.getId() : null;
	}
}
