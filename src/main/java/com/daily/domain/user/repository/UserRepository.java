package com.daily.domain.user.repository;

import com.daily.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
        Optional<User> findById(String id);

        @Query(value = "SELECT u " +
                "FROM User u " +
                "LEFT JOIN FETCH UserSites us " +
                "LEFT JOIN FETCH Site s")
        List<User> fetchUsersWithSite();
}
