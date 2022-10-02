package com.dlog.domain.user.domain;

import com.dlog.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserNo(String userNo);
    User findByUsername(String username);

    User findByUserNoAndToken(Long userNo, String token);
    List<User> findBySubscribeYnOrderByUserNo(String subscribeYn);

}
