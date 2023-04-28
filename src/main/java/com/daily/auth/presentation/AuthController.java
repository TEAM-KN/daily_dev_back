package com.daily.auth.presentation;

import com.daily.auth.application.AuthService;
import com.daily.user.dto.UserJoinRequest;
import com.daily.user.dto.UserLoginRequest;
import com.daily.user.dto.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public UserLoginResponse join(@Valid @RequestBody UserJoinRequest joinRequest) throws Exception {
        return authService.join(joinRequest);
    }

}
