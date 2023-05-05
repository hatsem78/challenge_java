package com.challenge_java.challenge_java.model.services;

import com.challenge_java.challenge_java.model.entity.User;
import com.challenge_java.challenge_java.model.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServicesImpl implements IUser {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User update(User user) {
        return null;
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}
