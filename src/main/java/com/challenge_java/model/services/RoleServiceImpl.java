package com.challenge_java.model.services;

import com.challenge_java.model.entity.ERole;
import com.challenge_java.model.entity.Role;
import com.challenge_java.model.repository.IRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class RoleServiceImpl implements IRole {

    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }


}
