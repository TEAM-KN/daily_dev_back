package com.daily.domain.user.dto;

import com.daily.domain.site.dto.SiteDto;
import com.daily.domain.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {

    private String email;
    private String nickname;
    private String imageUrl;
    private String subscribeYn;

    public UserDto(String email, String nickname, String imageUrl, String subscribeYn) {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.subscribeYn = subscribeYn;
    }

    public UserDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
        this.subscribeYn = user.getSubscribeYn();
    }

    public User toUser() {
        return User.builder()
                .email(getEmail())
                .nickname(getNickname())
                .imageUrl(getImageUrl())
                .subscribeYn(getSubscribeYn())
                .build();
    }

    @Data
    public static class UserWithSite extends UserDto {

        private List<SiteDto> sites;

        public UserWithSite(User user) {
            super(user);
            this.sites = user.getUserSites().stream().map(SiteDto::new).collect(Collectors.toList());
        }

    }

}
