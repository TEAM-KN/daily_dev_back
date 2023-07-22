package com.daily.domain.user.dto;

import com.daily.domain.user.domain.User;
import com.daily.global.common.dto.YN;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @Email
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    private String nickname;

    private List<String> siteCodes;


    @Data
    public static class UserFromSiteRequest {

        @NotBlank(message = "사용자 계정을 찾을 수 없습니다.")
        private String email;

        @Size(min = 1, message = "삭제 할 사이트를 1개 이상 선택해주세요.")
        private List<@NotBlank String> siteCodes;

        public UserFromSiteRequest(String email, List<String> siteCodes) {
            this.email = email;
            this.siteCodes = siteCodes;
        }

    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(getEmail())
                .nickname(getNickname())
                .password(passwordEncoder.encode(getPassword()))
                .subscribeYn(siteCodes.size() > 0 ? YN.Y.name() : YN.N.name())
                .build();
    }

}
