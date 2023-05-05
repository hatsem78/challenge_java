package com.challenge_java.challenge_java.model.repository;

import com.challenge_java.challenge_java.model.entity.ERole;
import com.challenge_java.challenge_java.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
