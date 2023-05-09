package com.challenge_java;

import com.challenge_java.model.entity.ERole;
import com.challenge_java.model.entity.Role;
import com.challenge_java.model.services.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;

@Slf4j
@Configuration
class LoadDatabase {

    @Autowired
    CommandLineRunner initDatabase(
            RoleServiceImpl roleService
    ) {
        final Role user = createRoleIfNotFound(roleService, ERole.ROLE_USER);
        final Role moderator = createRoleIfNotFound(roleService, ERole.ROLE_MODERATOR);
        final Role admin = createRoleIfNotFound(roleService, ERole.ROLE_ADMIN);

        return null;
    }


    @Transactional
    Role createRoleIfNotFound(RoleServiceImpl roleService, ERole name) {
        Role role = roleService.findByName(ERole.ROLE_USER).orElse(null);
        if (role == null) {
            role = new Role(name);
            roleService.save(role);
        }
        return role;
    }


}
