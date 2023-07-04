package com.daily.domain.user.dto;

import com.daily.domain.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String nickname;
    private String imageUrl;
    private String subscribeYn;

    public UserDto(String email, String password, String nickname, String imageUrl, String subscribeYn) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.subscribeYn = subscribeYn;
    }

    public UserDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
        this.subscribeYn = user.getSubscribeYn();
    }

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
