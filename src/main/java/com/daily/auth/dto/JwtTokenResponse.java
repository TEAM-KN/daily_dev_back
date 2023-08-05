package com.daily.auth.dto;

import lombok.Getter;

@Getter
public class JwtTokenResponse {

    private String accessToken;
    private String refreshToken;

    public JwtTokenResponse(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
