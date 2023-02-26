package com.dlog.domain.user.application;

import com.dlog.domain.user.dto.UserDto;
import com.dlog.domain.user.dto.UserJoinRequest;
import com.dlog.domain.user.dto.UserLoginRequest;
import com.dlog.domain.user.dto.UserLoginResponse;

public interface UserService {

    UserLoginResponse join(UserJoinRequest joinRq);
    UserLoginResponse login(UserLoginRequest loginRq);
    UserDto getUserByUsername(String email);

    UserDto refresh(UserDto user);
}
