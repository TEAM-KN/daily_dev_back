package com.news.dev.api.user.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class UserLoginResponse {
    private final String email;
    private final String nickName;
    private final String token;
}
