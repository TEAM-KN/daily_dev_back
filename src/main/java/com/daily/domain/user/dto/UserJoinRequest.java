package com.daily.domain.user.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private Long id;
    private String email;
    private String nickname;
    private String imageUrl;
    private String subscribeYn;
}
