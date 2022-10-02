package com.dlog.auth.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponse {
    private String username;
    private String nickname;
    private String password;
    private Long userNo;
    private String subscribeYn;
    private String accessToken;
    private String refreshToken;
}
