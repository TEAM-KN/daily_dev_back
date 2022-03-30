package com.news.dev.api.user.service;

import com.news.dev.api.user.dto.UserDto;
import com.news.dev.api.user.dto.UserJoinRequest;
import com.news.dev.api.user.dto.UserLoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserLoginResponse join(UserJoinRequest joinRq) throws Exception;
    UserDto getUserByUserNo(String userNo) throws Exception;
    UserDto getUserByUserEmail(String email) throws Exception;
}
