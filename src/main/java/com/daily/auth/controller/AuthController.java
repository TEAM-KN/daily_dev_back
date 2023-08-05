package com.daily.auth.controller;

import com.daily.auth.application.AuthService;
import com.daily.auth.dto.AccessTokenRenewRequest;
import com.daily.auth.dto.JwtTokenResponse;
import com.daily.auth.dto.LoginRequest;
import com.daily.auth.dto.LoginResponse;
import com.daily.global.common.response.CommonResponse;
import com.daily.domain.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/isCheck")
    public CommonResponse isCheck(@NotBlank(message = "이메일은 필수 입력 값 입니다.") @RequestParam String email) {
        return authService.isCheck(email);
    }

    @PostMapping("/login")
    public JwtTokenResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/join")
    public JwtTokenResponse join(@Valid @RequestBody UserRequest request) {
        return authService.join(request);
    }

    @PostMapping("/token/access")
    public CommonResponse token(@RequestBody @Valid AccessTokenRenewRequest tokenRenewRequest, HttpServletResponse response) {
        authService.generateRenewAccessToken(tokenRenewRequest, response);
        return new CommonResponse(HttpStatus.OK, "성공" );
    }
}
