package com.qrcode.login.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false)
	private String account;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String password;

	private String avatar;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private String position;

	private int follow;

	private int fans;

	private int actions;
}
