package com.daily.fixtures;

import com.daily.auth.dto.AccessTokenRenewRequest;
import com.daily.auth.dto.TokenDto;

public class AuthFixtures {

    public static final String ACCESS_TOKEN = "abcdef.abcdef.abcdef";
    public static final String REFRESH_TOKEN = "fedcba.fedcba.fedcba";
    public static final String TOKEN = "Bearer " + ACCESS_TOKEN;

    public static AccessTokenRenewRequest USER_RENEW_TOKEN_REQUEST() {
        return new AccessTokenRenewRequest(REFRESH_TOKEN);
    }

    public static TokenDto USER_RENEW_TOKEN_RESPONSE() {
        return new TokenDto(ACCESS_TOKEN, REFRESH_TOKEN);
    }
}
