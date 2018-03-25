package com.qrcode.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrcode.login.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
