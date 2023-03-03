package com.daily.domain.user.repository;

import com.daily.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findById(Long id);
        User findByEmail(String email);
}
