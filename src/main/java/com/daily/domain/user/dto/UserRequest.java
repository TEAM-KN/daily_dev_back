package com.daily.domain.user.dto;

import com.daily.domain.user.domain.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "사용자 계정을 찾을 수 없습니다.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    private String nickname;
    private String imageUrl;

    private MultipartFile imageFile;

    private List<String> siteCodes;


    @Data
    public static class UserFromSiteRequest {

        @NotBlank(message = "사용자 계정을 찾을 수 없습니다.")
        private String email;

        @NotBlank(message = "사용자 계정을 찾을 수 없습니다.")
        @Size(min = 1, message = "삭제 할 사이트를 1개 이상 선택해주세요.")
        private List<String> siteCodes;

        public UserFromSiteRequest() {}

    }

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
