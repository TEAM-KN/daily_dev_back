package com.daily.user.dto;

import com.daily.user.domain.User;
import lombok.*;

@Getter
public class UserLoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String subscribeYn;
    private String imageUrl;
    private String accessToken;
    private String refreshToken;

    public UserLoginResponse() {}

    public UserLoginResponse(User user) {
        this(user.getId(), user.getEmail(), user.getNickname(), user.getSubscribeYn(), user.getImageUrl(), null, null);
    }

    public UserLoginResponse(Long id, String email, String nickname, String subscribeYn, String imageUrl, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.subscribeYn = subscribeYn;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
