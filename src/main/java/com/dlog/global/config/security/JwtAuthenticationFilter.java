package com.dlog.global.config.security;

import com.dlog.domain.auth.application.AuthService;
import com.dlog.domain.auth.application.JwtTokenService;
import com.dlog.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenService jwtTokenService;
    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader("Authorization");

        if(token != null && jwtTokenService.validationToken(token)) {

            String payload = jwtTokenService.getPayload(token);

            Authentication authentication = this.getAuthentication(payload);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(final String payload) {
        User user = authService.findUser(payload);

        return new UsernamePasswordAuthenticationToken(user, "");
    }
}
