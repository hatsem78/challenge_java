package com.challenge_java.challenge_java.response;

import java.util.Date;
import java.util.List;

public class UserSignUpResponse {
	private String ids;
	private String username;
	private String email;
	private Date createAt;
	private Date lastLogin;
	private String token;
	private Boolean isActive;

	public UserSignUpResponse(
			String ids,
			String username,
			String email,
			Date createAt,
			Date lastLogin,
			String token,
			Boolean isActive
	) {
		this.ids = ids;
		this.username = username;
		this.email = email;
		this.createAt = createAt;
		this.lastLogin = lastLogin;
		this.token = token;
		this.isActive = isActive;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}
}
