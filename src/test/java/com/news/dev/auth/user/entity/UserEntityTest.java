package com.news.dev.auth.user.entity;



import com.news.dev.jpa.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class UserEntityTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        UserEntity user = UserEntity.builder()
                .username("schulnoh@gmail.com")
                .password(passwordEncoder.encode("test1234"))
                .nickname("test")
                .build();

        entityManager.persist(user);
    }

    @Test
    public void userSearch() {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

    }

}