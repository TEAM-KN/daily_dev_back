package com.daily.domain.user.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String subscribeYn;
    private String imageUrl;
    private String accessToken;
    private String refreshToken;
}
