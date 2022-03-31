package com.news.dev.api.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserJoinRequest {
    private final String email;
    private final String password;
    private final String nickname; // 닉네임
    private final String userNo;
    //private final String subscribeYn; //구독 여부 (기본 : N)
}
