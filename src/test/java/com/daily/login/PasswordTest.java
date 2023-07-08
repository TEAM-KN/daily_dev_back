package com.daily.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("local")
public class PasswordTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncode() {
        String password = "1234";
        String encode = passwordEncoder.encode(password);

        System.out.println("encode password: " + encode);
    }
}
