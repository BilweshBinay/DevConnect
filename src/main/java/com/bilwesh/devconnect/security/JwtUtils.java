package com.bilwesh.devconnect.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String jwtSecrect;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private SecretKey getSiginingKey() {
        byte[] keyBytes= Decoders.BASE64.decode(jwtSecrect);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String usernameOrEmail) {
        return Jwts.builder()
                .subject(usernameOrEmail)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSiginingKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSiginingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSiginingKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
