package com.news.dev.auth.user.service;

import com.news.dev.auth.user.dto.UserDto;
import com.news.dev.auth.user.dto.UserJoinRequest;
import com.news.dev.auth.user.dto.UserLoginRequest;
import com.news.dev.auth.user.dto.UserLoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserLoginResponse join(UserJoinRequest joinRq) throws Exception;
    UserLoginResponse login(UserLoginRequest loginRq) throws Exception;
    UserDto getUserByUserEmail(String email) throws Exception;
}
