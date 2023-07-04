package com.daily.fixtures;

import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserDto;

public class UserFixtures {

    // 일반 사용자
    public static final String email = "test1234@gmail.com";
    public static final String password = "1234";
    public static final String nickname = "tester";
    public static final String imageUrl = "/person.png";
    public static final String subscribeYn = "Y";
    public static final UserDto userResponse = new UserDto(email, password, nickname, imageUrl, subscribeYn);

    public static User user() {
        return new User(email, password, nickname, imageUrl, subscribeYn);
    }

}