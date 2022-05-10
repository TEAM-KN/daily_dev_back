package com.news.dev.auth.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinRequest {
    private String username;
    private String password;
    private String nickname; // 닉네임
    private Long userNo;
    private String subscribeYn; //구독 여부 (기본 : N)
}
