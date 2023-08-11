package com.daily.domain.user.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserResponse {

    @Data
    public static class Update {

        private String email;

        private String nickname;

    }

}
