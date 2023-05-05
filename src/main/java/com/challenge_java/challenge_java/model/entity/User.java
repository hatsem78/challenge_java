package com.challenge_java.challenge_java.model.entity;


import com.challenge_java.challenge_java.utils.decorator.PasswordMatches;
import com.challenge_java.challenge_java.utils.decorator.ValidEmail;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

import static org.hibernate.annotations.CascadeType.*;

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

    @Column(name = "ids")
    private String ids;

    @Size(max = 20)
    @NotBlank
    private String username;

    @Size(max = 50)
    @NotBlank
    @ValidEmail
    private String email;

    @Size(max = 120)
    @NotBlank
    @PasswordMatches
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ SAVE_UPDATE, MERGE, PERSIST})
    @JoinTable(name = "user_phones",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    private Set<Phone> phone = new HashSet<>();
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Column(name = "last_login")
    @Temporal(TemporalType.DATE)
    private Date lastLogin;

    private String token;

    @Column(name = "is_active")
    private Boolean isActive;

    public User() {
        this.ids = UUID.randomUUID().toString();
        this.isActive = false;
    }

    public Set<Phone> getPhone() {
        return phone;
    }

    public void setPhone(Set<Phone> phone) {
        this.phone = phone;
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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