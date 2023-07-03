package com.daily.global.filter;

import com.daily.auth.application.JwtTokenProvider;
import com.daily.auth.dto.LoginResponse;
import com.daily.global.common.dto.Constants;
import com.daily.global.security.AuthenticationToken;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserLoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/auth/login");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginRequest userLoginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);

        AuthenticationToken authenticationToken = new AuthenticationToken(null, userLoginRequest.getEmail(), userLoginRequest.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        String accessToken = Constants.BEARER + jwtTokenProvider.createAccessToken(user.getEmail());
        response.addHeader(Constants.BEARER.getKey(), accessToken);

        LoginResponse loginResponse = new LoginResponse(HttpStatus.OK.value(), "로그인 성공");
        this.writeJsonResponse(response, HttpStatus.OK, loginResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, Object body) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter writer = response.getWriter()) {
            objectMapper.writeValue(writer, body);
        }
    }
}
