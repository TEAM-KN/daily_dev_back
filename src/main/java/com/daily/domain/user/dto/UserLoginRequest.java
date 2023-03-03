package com.daily.domain.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequest {
    private String username;
    private String nickname;
    private String password;
    private Long userNo;
    private String subscribeYn;

}
