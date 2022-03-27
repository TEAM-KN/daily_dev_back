package com.news.dev.api.user.service;

import com.news.dev.api.user.dao.UserJoinRequest;
import com.news.dev.api.user.dao.UserLoginResponse;

public interface UserService {

    UserLoginResponse join(UserJoinRequest joinRq) throws Exception;
}
