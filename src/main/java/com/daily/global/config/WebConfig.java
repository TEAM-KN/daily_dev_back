package com.daily.global.config;

import com.daily.auth.application.AuthInterceptor;
import com.daily.auth.application.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> corsAllowedOrigins;
    private final JwtTokenProvider jwtTokenProvider;

    public WebConfig(@Value("${web.cors.allowed-origins}") final List<String> corsAllowedOrigins,
                     final JwtTokenProvider jwtTokenProvider) {
        this.corsAllowedOrigins = corsAllowedOrigins;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwtTokenProvider))
                .addPathPatterns("/api/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] patterns = corsAllowedOrigins.toArray(String[]::new);

        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns(patterns);
    }
}
