package com.daily.domain.user.dto;

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
    private String username;
    private String nickname;
    private String pwd;
    private LocalDateTime joinDtm;
    private LocalDateTime updateDtm;
    private Long userNo;
    private String subscribeYn;
    private String accessToken;
    private String refreshToken;

}
