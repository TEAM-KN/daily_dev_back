package com.news.dev.auth.user.service;

import com.news.dev.auth.user.dto.*;

public interface UserService {

    UserLoginResponse join(UserJoinRequest joinRq) throws Exception;
    UserLoginResponse login(UserLoginRequest loginRq) throws Exception;
    UserDto getUserByUsername(String email) throws Exception;

    UserDto refresh(UserDto user) throws Exception;
}
