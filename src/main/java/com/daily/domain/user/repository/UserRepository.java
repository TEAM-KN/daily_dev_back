package com.daily.domain.user.repository;

import com.daily.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
        Optional<User> findById(String id);

        @Query(value = "SELECT u " +
                "FROM User u " +
                "LEFT JOIN FETCH u.userSites us " +
                "LEFT JOIN FETCH  us.site " +
                "WHERE u.email = :email")
        Optional<User> fetchUserWithSite(String email);

        @Modifying
        @Query(value = "UPDATE User u SET u.subscribeYn = :subscribeYn WHERE u.email = :email")
        void updateUserBySubscribeYn(String email, String subscribeYn);

        @Modifying
        @Query(value = "UPDATE User u SET u.nickname = :nickname WHERE u.email = :email")
        User updateUserByNickname(String email, String nickname);

        @Modifying
        @Query(value = "UPDATE User u SET u.password = :password WHERE u.email = :email")
        User updateUserByPassword(String email, String password);

}
