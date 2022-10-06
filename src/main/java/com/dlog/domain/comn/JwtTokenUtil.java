package com.dlog.domain.comn;


import com.dlog.global.config.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {

    private String secretKey = "DEV_NEWS";
    private final long accessTokenExpiration = 30 * 60 * 1000L; // 토큰 유효기간 (30분)
    private final long refreshTokenExpiration = 1000L * 60 * 69 * 24 * 7;

    // Bean
    private final CustomUserDetailService customUserDetailService;

    @PostConstruct
    protected void init() { // 객체 초기화, secretKey를 base64로 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Access Token Create
    public String createToken(String email) {
        Date now = new Date();

        return Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(now.getTime() + accessTokenExpiration))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    // Refresh Token Create
    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Jwt 인증 정보 조회
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUser(token));
        log.debug("user : {}", userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Token으로 회원 정보 조회
    public String getUser(String token) {

        String user = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        log.debug("user: {}", user);
        return user;
    }

    // Request의 Header에서 Token을 가져오기 - "X-AUTH-TOKEN" : "TOKEN"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // Token의 유효성 + 만료일자 확인
    public boolean validationToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
