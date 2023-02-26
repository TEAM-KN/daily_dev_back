package com.dlog.domain.user.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private String username;
    private String nickname;
    private String password;
    private Long userNo;
    private String subscribeYn;
    private String accessToken;
    private String refreshToken;
}
