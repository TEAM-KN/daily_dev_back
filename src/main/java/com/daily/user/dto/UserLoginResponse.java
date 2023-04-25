package com.daily.user.dto;

import com.daily.user.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String subscribeYn;
    private String imageUrl;

    public UserLoginResponse(User user) {
        this(user.getEmail(), user.getNickname(), user.getSubscribeYn(), user.getImageUrl());
    }

    public UserLoginResponse(String email, String nickname, String subscribeYn, String imageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.subscribeYn = subscribeYn;
        this.imageUrl = imageUrl;
    }
}
