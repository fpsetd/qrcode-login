package com.qrcode.login.repository;

import com.qrcode.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByAccount(String account);
}
