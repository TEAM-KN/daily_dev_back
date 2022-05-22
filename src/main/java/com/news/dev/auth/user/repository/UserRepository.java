package com.news.dev.auth.user.repository;

import com.news.dev.auth.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserNo(String userNo);
    UserEntity findByUsername(String username);
}
