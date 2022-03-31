package com.news.dev.api.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserLoginRequest {
    private final String email;
    private final String nickname;
    private final String password;
    private final String userNo;
}
