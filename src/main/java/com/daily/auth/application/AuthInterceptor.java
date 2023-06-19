package com.daily.auth.application;

import com.daily.auth.exception.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider tokenProvider;

    public AuthInterceptor(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = AuthExtractor.extractAccessToken(request);
            tokenProvider.validateToken(token);
        } catch (RuntimeException e) {
            throw new InvalidTokenException();
        }

        return true;
    }
}
