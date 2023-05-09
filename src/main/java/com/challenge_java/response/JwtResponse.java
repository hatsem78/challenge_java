package com.challenge_java.response;

import com.challenge_java.model.entity.Phone;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtResponse {

  private String type = "Bearer";
  private String id;
  private Date createAt;
  private Date lastLogin;
  private String token;
  private Boolean isActive;
  private String username;
  private String email;
  private String password;
  private Set<Phone> phone = new HashSet<>();

  public JwtResponse(
          String id,
          Date createAt,
          Date lastLogin,
          String token,
          Boolean isActive,
          String username,
          String email,
          String password,
          Set<Phone> phone
  ) {
    this.id = id;
    this.createAt = createAt;
    this.lastLogin = lastLogin;
    this.token = token;
    this.isActive = isActive;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Phone> getPhone() {
    return phone;
  }

  public void setPhone(Set<Phone> phone) {
    this.phone = phone;
  }
}
