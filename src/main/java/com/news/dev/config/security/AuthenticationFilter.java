package com.news.dev.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.dev.api.user.dto.UserDto;
import com.news.dev.api.user.dto.UserJoinRequest;
import com.news.dev.api.user.service.UserService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UserJoinRequest joinRQ = new ObjectMapper().readValue(request.getInputStream(), UserJoinRequest.class);

            return getAuthenticationManager()
                    .authenticate(
                        new UsernamePasswordAuthenticationToken(
                            joinRQ.getEmail(),
                            joinRQ.getPassword(),
                            new ArrayList<>()
                        )
                    );
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        try {
            String userName = ((User)authResult.getPrincipal()).getUsername();
            UserDto user = userService.getUserByUserEmail(userName);

            String token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                    .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                    .compact();

            response.addHeader("token", token);
        } catch (Exception e) {
            throw new NullPointerException();
        }

    }
}
