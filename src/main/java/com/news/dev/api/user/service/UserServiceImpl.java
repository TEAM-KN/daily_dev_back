package com.news.dev.api.user.service;

import com.news.dev.api.user.dao.UserJoinRequest;
import com.news.dev.api.user.dao.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserLoginResponse join(UserJoinRequest joinRq) throws Exception {
        return null;
    }
}
