package com.news.dev.auth.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponse {
    private String email;
    private String nickname;
    private String password;
    private Long userNo;
    private String token;
}
