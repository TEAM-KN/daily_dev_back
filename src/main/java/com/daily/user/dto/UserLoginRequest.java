package com.daily.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequest {
    private String email;
    private String nickname;
    private String password;
    private String subscribeYn;

}
