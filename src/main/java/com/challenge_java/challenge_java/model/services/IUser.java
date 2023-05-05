package com.challenge_java.challenge_java.model.services;

import com.challenge_java.challenge_java.model.entity.User;

import java.util.Optional;

public interface IUser {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    User update(User user);
    User save(User user);
}
