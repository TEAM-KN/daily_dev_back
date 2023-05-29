package com.daily.domain.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {

    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;

}
