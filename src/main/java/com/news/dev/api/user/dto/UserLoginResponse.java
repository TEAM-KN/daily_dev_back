package com.news.dev.api.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class UserLoginResponse {
    private final String email;
    private final String nickname;
    private final String password;
    private final String userNo;
    private final String token;
}
