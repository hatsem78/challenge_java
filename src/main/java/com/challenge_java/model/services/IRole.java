package com.challenge_java.model.services;

import com.challenge_java.model.entity.ERole;
import com.challenge_java.model.entity.Role;

import java.util.Optional;

public interface IRole {
    Optional<Role> findByName(ERole name);
}
