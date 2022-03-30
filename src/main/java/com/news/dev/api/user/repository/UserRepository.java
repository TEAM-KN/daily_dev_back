package com.news.dev.api.user.repository;

import com.news.dev.api.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUserNo(String userNo);
    UserEntity findByUserEmail(String email);
}
