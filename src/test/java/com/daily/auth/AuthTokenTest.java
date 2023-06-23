package com.daily.auth;

import com.daily.auth.application.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
public class AuthTokenTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void createAccessTokenTest() {
        String token = jwtTokenProvider.createAccessToken("admin");
        System.out.println("token : " + token);

    }


}
