package com.challenge_java.challenge_java.model.entity;


import com.challenge_java.challenge_java.utils.decorator.PasswordMatches;
import com.challenge_java.challenge_java.utils.decorator.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.Size;
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 20)
  private String username;

  @Size(max = 50)
  @ValidEmail
  private String email;

  @Size(max = 120)
  @PasswordMatches
  private String password;

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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



}