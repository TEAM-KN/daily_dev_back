package com.news.dev.api.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private String email;
    private String nickname;
    private String pwd;
    private Date joinDtm;
    private Date updateDtm;
    private String userNo;

    // 이메일, 패스워드 암호화
    private String encryptEmail;
    private String encryptPwd;
}
