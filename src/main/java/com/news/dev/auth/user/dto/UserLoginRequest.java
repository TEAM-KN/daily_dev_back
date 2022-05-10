package com.news.dev.auth.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequest {
    private String username;
    private String nickname;
    private String password;
    private Long userNo;
    private String subscribeYn;

}
