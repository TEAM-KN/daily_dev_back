package com.daily.domain.user.dto;

import com.daily.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "이메일은 Null 또는 공백일 수 없습니다.")
    private String email;


    private String password;

    private String nickname;
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
