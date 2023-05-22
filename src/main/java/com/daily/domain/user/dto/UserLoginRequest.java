package com.daily.domain.user.dto;

import lombok.*;

@Data
public class UserLoginRequest {
    private String email;
    private String nickname;
    private String password;
    private String subscribeYn;

}
