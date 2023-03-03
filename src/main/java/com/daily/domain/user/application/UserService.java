package com.daily.domain.user.application;

import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserJoinRequest;
import com.daily.domain.user.dto.UserLoginRequest;
import com.daily.domain.user.dto.UserLoginResponse;

public interface UserService {

    UserLoginResponse join(UserJoinRequest joinRq);
    UserLoginResponse login(UserLoginRequest loginRq);
    UserDto getUserByUsername(String email);

    UserDto refresh(UserDto user);
}
