package com.daily.user.dto;

import com.daily.user.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

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
