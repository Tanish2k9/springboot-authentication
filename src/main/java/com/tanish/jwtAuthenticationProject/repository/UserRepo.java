package com.tanish.jwtAuthenticationProject.repository;

import com.tanish.jwtAuthenticationProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
