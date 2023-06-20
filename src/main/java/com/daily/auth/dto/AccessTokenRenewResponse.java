package com.daily.auth.dto;

import lombok.Data;

@Data
public class AccessTokenRenewResponse {

    private String accessToken;
    private String refreshToken;

    public AccessTokenRenewResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
