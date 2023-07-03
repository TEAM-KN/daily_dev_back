package com.daily.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private int code;
    private String message;

    public LoginResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
