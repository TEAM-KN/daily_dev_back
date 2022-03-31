package com.news.dev.api.user.controller;

import com.news.dev.api.user.dto.UserJoinRequest;
import com.news.dev.api.user.dto.UserLoginResponse;
import com.news.dev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserLoginResponse> join(@RequestBody UserJoinRequest joinRq) throws Exception {
        UserLoginResponse loginRs = null;

        loginRs  = userService.join(joinRq);

        return ResponseEntity.status(HttpStatus.CREATED).body(loginRs);
    }

}
