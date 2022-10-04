package com.dlog.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findById(Long id);
        User findByEmail(String email);

        User findByUserNoAndToken(Long userNo, String token);
        List<User> findBySubscribeYnOrderByUserNo(String subscribeYn);

}
