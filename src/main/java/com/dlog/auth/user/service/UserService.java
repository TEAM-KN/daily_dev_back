package com.dlog.auth.user.service;

import com.dlog.auth.user.dto.UserDto;
import com.dlog.auth.user.dto.UserJoinRequest;
import com.dlog.auth.user.dto.UserLoginRequest;
import com.dlog.auth.user.dto.UserLoginResponse;
import com.news.dev.auth.user.dto.*;

public interface UserService {

    UserLoginResponse join(UserJoinRequest joinRq) throws Exception;
    UserLoginResponse login(UserLoginRequest loginRq) throws Exception;
    UserDto getUserByUsername(String email) throws Exception;

    UserDto refresh(UserDto user) throws Exception;
}
