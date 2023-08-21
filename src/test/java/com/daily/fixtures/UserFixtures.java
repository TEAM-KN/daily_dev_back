package com.daily.fixtures;

import com.daily.domain.site.dto.SiteDto;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserRequest;

import java.util.List;

import static com.daily.fixtures.SiteFixtures.*;

public class UserFixtures {

    // 일반 사용자
    public static final String email = "test1234@gmail.com";
    public static final String password = "1234";
    public static final String nickname = "tester";
    public static final String imageUrl = "/person.png";
    public static final String subscribeYn = "Y";
    public static final List<String> siteCode = List.of("NAVER", "WOO", "NAVER");
    public static final List<SiteDto> sites = List.of(WOO_SITE_DTO, KAKAO_SITE_DTO, NAVER_SITE_DTO);

    public static final UserDto userResponse = new UserDto(email, nickname, imageUrl, subscribeYn);
    public static final UserDto.UserWithSite userWithSiteResponse = new UserDto.UserWithSite(email, nickname, imageUrl, subscribeYn, sites);

    public static UserRequest.UserFromSiteRequest USER_REQUEST() {
        return new UserRequest.UserFromSiteRequest(email, siteCode);
    }

    public static UserRequest.Password USER_CHECK_REQUEST() {
        return new UserRequest.Password(email, password);
    }

    public static UserRequest.Password USER_PASSWORD_UPDATE_REQUEST() {
        return new UserRequest.Password(email, password);
    }

    public static User USER() {
        return new User(email, password, nickname, imageUrl, subscribeYn);
    }

}
