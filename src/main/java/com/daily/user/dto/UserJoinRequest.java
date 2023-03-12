package com.daily.user.dto;

import com.daily.user.domain.User;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private Long id;
    private String email;
    private String nickname;
    private String imageUrl;
    private String password;
    private String subscribeYn;

    public User toUser() {
        return User.builder()
                .email(getEmail())
                .nickname(getNickname())
                .imageUrl(getImageUrl())
                .password(getPassword())
                .subscribeYn(getSubscribeYn())
                .build();
    }

}
