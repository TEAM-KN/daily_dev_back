package com.daily.fixtures;

import com.daily.auth.dto.AccessTokenRenewRequest;
import com.daily.auth.dto.AccessTokenRenewResponse;

public class AuthFixtures {

    public static final String ACCESS_TOKEN = "abcdef.abcdef.abcdef";
    public static final String REFRESH_TOKEN = "fedcba.fedcba.fedcba";
    public static final String TOKEN = "Bearer " + ACCESS_TOKEN;

    public static AccessTokenRenewRequest USER_RENEW_TOKEN_REQUEST() {
        return new AccessTokenRenewRequest(REFRESH_TOKEN);
    }

    public static AccessTokenRenewResponse USER_RENEW_TOKEN_RESPONSE() {
        return new AccessTokenRenewResponse(ACCESS_TOKEN, REFRESH_TOKEN);
    }
}
