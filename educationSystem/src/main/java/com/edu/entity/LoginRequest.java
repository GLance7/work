package com.edu.entity;

/**
* @Author: wl
* @Description: 当前登录用户控制器
*/

public class LoginRequest {
		private String username;
		private String password;
		private String imageCode;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
}
