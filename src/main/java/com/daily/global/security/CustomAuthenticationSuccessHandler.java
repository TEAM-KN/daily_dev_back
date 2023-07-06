package com.daily.global.security;

import com.daily.auth.application.JwtTokenProvider;
import com.daily.auth.dto.LoginResponse;
import com.daily.domain.user.domain.User;
import com.daily.global.common.dto.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String accessToken = Constants.BEARER + jwtTokenProvider.createAccessToken(user.getEmail());
        response.addHeader(Constants.BEARER.getKey(), accessToken);

        LoginResponse loginResponse = new LoginResponse(HttpStatus.OK.value(), "로그인 성공");
        this.writeJsonResponse(response, HttpStatus.OK, loginResponse);
    }

    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, Object body) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter writer = response.getWriter()) {
            objectMapper.writeValue(writer, body);
        }
    }
}
