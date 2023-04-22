package com.daily.user.dto;

import com.daily.user.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {

    @NotNull(message = "사용자 계정을 찾을 수 없습니다.")
    @NotBlank(message = "사용자 계정을 찾을 수 없습니다.")
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
