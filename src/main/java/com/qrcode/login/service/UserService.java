package com.qrcode.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qrcode.login.entity.User;
import com.qrcode.login.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User findById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public String getAvatar(String account) {
		return userRepository.findAvatarByAccount(account).orElse("");
	}

	public Integer login(String account, String password) {
		User user = userRepository.findByAccount(account).orElse(null);
		return (user != null && user.getPassword().equals(password)) ? user.getId() : null;
	}
}
