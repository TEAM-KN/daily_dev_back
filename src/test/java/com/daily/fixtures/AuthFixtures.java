package com.daily.fixtures;

import com.daily.auth.dto.*;
import com.daily.domain.user.dto.UserRequest;

import java.util.List;

public class AuthFixtures {

    public static final String email = "scnoh0617@gmail.com";
    public static final String password = "1234";
    public static final String nickname = "승똘";
    public static final List<String> siteCode = List.of("NAVER", "WOO", "NAVER");
    public static final String ACCESS_TOKEN = "abcdef.abcdef.abcdef";
    public static final String REFRESH_TOKEN = "fedcba.fedcba.fedcba";
    public static final String TOKEN = "Bearer " + ACCESS_TOKEN;

    public static LoginRequest LOGIN_REQUEST() {
        return new LoginRequest(email, password);
    }

    public static JwtTokenResponse LOGIN_RESPONSE() {
        return new JwtTokenResponse(ACCESS_TOKEN, REFRESH_TOKEN);
    }

    public static UserRequest JOIN_REQUEST() {
        return new UserRequest(email, password, nickname, siteCode);
    }

    public static AccessTokenRenewRequest USER_RENEW_TOKEN_REQUEST() {
        return new AccessTokenRenewRequest(REFRESH_TOKEN);
    }

    public static TokenDto USER_RENEW_TOKEN_RESPONSE() {
        return new TokenDto(ACCESS_TOKEN, REFRESH_TOKEN);
    }
}
