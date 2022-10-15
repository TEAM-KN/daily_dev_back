package com.dlog.domain.user.dto;

import lombok.*;

@Getter
@Setter
public class UserJoinRequest {
    private Long id;
    private String email;
    private String nickname;
    private String imageUrl;
    private String subscribeYn;
}
