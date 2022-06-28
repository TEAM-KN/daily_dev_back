package com.news.dev.jpa.repository;

import com.news.dev.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserNo(String userNo);
    UserEntity findByUsername(String username);

    UserEntity findByUserNoAndToken(Long userNo, String token);
    List<UserEntity> findBySubscribeYnOrderByUserNo(String subscribeYn);

}
