package com.daily.user.dto;

import com.daily.user.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String nickname;
    private String password;
    private String imageUrl;
    private String subscribeYn;

    public User toUser() {
        return User.builder()
                .email(getEmail())
                .nickname(getNickname())
                .password(getPassword())
                .imageUrl(getImageUrl())
                .subscribeYn(getSubscribeYn())
                .build();
    }

}
