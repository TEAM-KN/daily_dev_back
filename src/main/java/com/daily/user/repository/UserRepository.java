package com.daily.user.repository;

import com.daily.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findById(Long id);
        User findByEmail(String email);
}
