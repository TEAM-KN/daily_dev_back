package com.dlog.domain.user.application;

import com.dlog.domain.user.dto.UserDto;
import com.dlog.domain.user.dto.UserJoinRequest;
import com.dlog.domain.user.dto.UserLoginRequest;
import com.dlog.domain.user.dto.UserLoginResponse;
import com.news.dev.auth.user.dto.*;

public interface UserService {

    UserLoginResponse join(UserJoinRequest joinRq) throws Exception;
    UserLoginResponse login(UserLoginRequest loginRq) throws Exception;
    UserDto getUserByUsername(String email) throws Exception;

    UserDto refresh(UserDto user) throws Exception;
}
