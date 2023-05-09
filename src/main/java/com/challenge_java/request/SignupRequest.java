package com.challenge_java.request;

import com.challenge_java.model.entity.User;
import com.challenge_java.utils.decorator.PasswordMatches;
import com.challenge_java.utils.decorator.ValidEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class SignupRequest {

    @Size(min = 3, max = 50)
    private String name;

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
            String name,
            String password,
            String email,
            List<PhoneRequest> phoneList
    ) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phones = phoneList;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = name;
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
        return new SignupRequest(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                phoneList
        );
    }
}
