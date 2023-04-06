package com.daily.auth.presentation;

import com.daily.auth.application.AuthService;
import com.daily.user.application.UserService;
import com.daily.user.dto.UserDto;
import com.daily.user.dto.UserJoinRequest;
import com.daily.user.dto.UserLoginRequest;
import com.daily.user.dto.UserLoginResponse;
import com.daily.comn.exception.ResponseEntityHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class AuthController extends ResponseEntityHandler {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody UserJoinRequest joinRq) throws Exception {
        UserLoginResponse loginRs;

        loginRs  = authService.join(joinRq);

        return success(loginRs, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest loginRq) throws Exception {
        UserLoginResponse loginRs = authService.login(loginRq);

        return success(loginRs);
    }

}
