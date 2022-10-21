package com.dlog.domain.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class JwtTokenServiceTest {

    private static final String secretKey = "ZGxvZy1zZWN1cml0eS1qd3QtdG9rZW4tc2VjcmV0LWtleQ==";
    private static final long accessTokenExpiration = 1800L;
    private static final long refreshTokenExpiration = 1800L;

    static final String payload = "seungchul";

    private JwtTokenService jwtTokenService = new JwtTokenService(secretKey, accessTokenExpiration, refreshTokenExpiration);

    @DisplayName("토큰 생성을 생성하고 검증한다.")
    @Test
    void createTokenAndValid() {

        // given & when
        String jwt = jwtTokenService.createAccessToken(payload);
        Claims claims = jwtTokenService.getClaims(jwt);

        // then
        assertThat(claims.getSubject()).isEqualTo("seungchul");
    }

    @DisplayName("토큰의 유효 시간을 검증한다.")
    @Test
    void expiredTokenAndValid() {
        // given & when
        String jwt = jwtTokenService.createAccessToken(payload);

        // then
        assertThat(jwtTokenService.validationToken(jwt)).isTrue();

    }


}