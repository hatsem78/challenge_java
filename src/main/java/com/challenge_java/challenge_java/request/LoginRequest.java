package com.challenge_java.challenge_java.request;

import com.challenge_java.challenge_java.model.entity.Phone;
import com.challenge_java.challenge_java.model.entity.User;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

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
}