package com.challenge_java.challenge_java.request;

import com.challenge_java.challenge_java.model.entity.Phone;
import com.challenge_java.challenge_java.model.entity.User;
import com.challenge_java.challenge_java.utils.decorator.PasswordMatches;
import com.challenge_java.challenge_java.utils.decorator.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @ValidEmail
    private String email;

    private Set<String> role;

    private List<PhoneRequest> phones;

    @NotBlank
    @PasswordMatches
    private String password;

    public SignupRequest() {
    }

    public SignupRequest(
            String username,
            String password,
            String email,
            List<PhoneRequest> phoneList
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phones = phoneList;
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

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public List<PhoneRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequest> phones) {
        this.phones = phones;
    }

    public static SignupRequest build(User user, List<PhoneRequest> phoneList) {
    /*List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());*/

        return new SignupRequest(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                phoneList
        );
    }
}
