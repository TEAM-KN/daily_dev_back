package com.news.dev.auth.user.controller;

import com.news.dev.auth.user.dto.*;
import com.news.dev.auth.user.service.UserService;
import com.news.dev.response.ResponseEntityHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class UserController extends ResponseEntityHandler {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody UserJoinRequest joinRq) throws Exception {
        UserLoginResponse loginRs = null;

        loginRs  = userService.join(joinRq);

        return success(loginRs, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest loginRq) throws Exception {
        UserLoginResponse loginRs = userService.login(loginRq);

        return success(loginRs);
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info(@RequestBody UserDto requestDto) throws Exception {
        UserDto responseDto = userService.getUserByUsername(requestDto.getUsername());

        return success(responseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody UserDto user) {
        return success(user);
    }

}
