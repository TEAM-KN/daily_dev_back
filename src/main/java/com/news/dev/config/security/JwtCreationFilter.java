package com.news.dev.config.security;

import com.news.dev.auth.user.dto.UserDto;
import com.news.dev.auth.user.service.UserService;
import com.news.dev.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtCreationFilter extends GenericFilterBean {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("in JwtCreationFilter");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            String username = ((User)authentication.getPrincipal()).getUsername();
            UserDto user = userService.getUserByUserEmail(username);

            if(authentication != null && getJwtToRequest((HttpServletRequest) request)) {
                log.info("Jwt Token Creating !");

                String token = jwtTokenUtil.createToken(user.getEmail());
                setJwtToResponse((HttpServletResponse)response, token);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new NullPointerException("User is Null..");
        }

    }

    // Header에 토큰이 있는지 확인
    protected boolean getJwtToRequest(HttpServletRequest request) {
        String existToken = request.getHeader("X-AUTH-TOKEN");
        return existToken == null;
    }

    protected void setJwtToResponse(HttpServletResponse response, String token)
            throws IOException {
        response.setHeader("X-AUTH-TOKEN", token);
        response.getWriter().flush();
    }
}
