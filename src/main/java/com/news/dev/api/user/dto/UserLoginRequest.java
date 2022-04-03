package com.news.dev.api.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequest {
    private String email;
    private String nickname;
    private String password;
    private Long userNo;
    private String token;
}
