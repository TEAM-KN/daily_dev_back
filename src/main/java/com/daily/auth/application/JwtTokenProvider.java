package com.daily.auth.application;


import com.daily.auth.exception.ExpiredTokenException;
import com.daily.auth.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey tokenSecretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;


    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}")  final String secretKey,
                            @Value("${security.jwt.token.access.expire-length}") final long accessTokenExpiration,
                            @Value("${security.jwt.token.refresh.expire-length}") final long refreshTokenExpiration) {
        this.tokenSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }


    public String createAccessToken(final String payload) {
        return createToken(payload, this.accessTokenExpiration);
    }

    public String createRefreshToken(final String payload) {
        return createToken(payload, this.refreshTokenExpiration);
    }

    public String createToken(final String payload, final Long validTokenExpiration) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validTokenExpiration);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(tokenSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    public String getPayload(final String token) {
        return Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

}
