package com.daily.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccessTokenRenewRequest {

    @NotBlank(message = "refresh token can not be blank")
    private String refreshToken;

    public AccessTokenRenewRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
