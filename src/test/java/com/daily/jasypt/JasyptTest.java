package com.daily.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JasyptTest {

    @Autowired
    @Qualifier("jasyptStringEncryptor")
    private StringEncryptor stringEncryptor;

    @Test
    void propertyEncrypt() {
        String message = "test";
        String encryptMessage = stringEncryptor.encrypt(message);

        System.out.println(encryptMessage);
        assertEquals(message, stringEncryptor.decrypt(encryptMessage));
    }

}
