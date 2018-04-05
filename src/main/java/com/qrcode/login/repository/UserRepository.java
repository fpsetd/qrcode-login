package com.qrcode.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.qrcode.login.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select avatar from User where account = ?1")
	Optional<String> findAvatarByAccount(String account);
	Optional<User> findByAccount(String account);
}
