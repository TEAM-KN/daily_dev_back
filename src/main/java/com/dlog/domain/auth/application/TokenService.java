package com.dlog.domain.auth.application;

import com.dlog.domain.auth.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    private final SecretKey key;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenService(@Value("${security.jwt.secret-key}") final String secretKey,
                        @Value("${security.jwt.access.token-expire}") final long accessTokenExpiration,
                        @Value("${security.jwt.refresh.token-expire}") final long refreshTokenExpiration) {

        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String createAccessToken(final String payload) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validationToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return claims.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("권한이 없습니다.");
        }

    }
}
