package com.news.dev.auth.user.dto;

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
    private String pwd;
    private LocalDateTime joinDtm;
    private LocalDateTime updateDtm;
    private Long userNo;

    // 이메일, 패스워드 암호화
    private String encryptEmail;
    private String encryptPwd;
}
