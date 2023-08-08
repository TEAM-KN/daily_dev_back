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

    public UserDto(final String email, final String nickname, final String imageUrl, final String subscribeYn) {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.subscribeYn = subscribeYn;
    }

    public UserDto(final User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
        this.subscribeYn = user.getSubscribeYn();
    }

    @Data
    public static class UserWithSite extends UserDto {

        private List<SiteDto> sites;

        public UserWithSite(final String email, final String nickname, final String imageUrl, final String subscribeYn, final List<SiteDto> sites) {
            super(email, nickname, imageUrl, subscribeYn);
            this.sites = sites;
        }

        public UserWithSite(final User user) {
            super(user);
            this.sites = user.getUserSites().stream().map(SiteDto::new).collect(Collectors.toList());
        }

    }

}
