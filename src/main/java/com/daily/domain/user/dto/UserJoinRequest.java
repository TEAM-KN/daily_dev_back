package com.daily.domain.user.dto;

import com.daily.domain.user.domain.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {

    @NotBlank(message = "사용자 계정을 찾을 수 없습니다.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    private String nickname;
    private String imageUrl;

    private MultipartFile imageFile;

    private String[] siteCodes;


    public User toUser(String imageUrl) {
        return User.builder()
                .email(getEmail())
                .nickname(getNickname())
                .password(getPassword())
                .subscribeYn("N")
                .imageUrl(imageUrl)
                .build();
    }

}
