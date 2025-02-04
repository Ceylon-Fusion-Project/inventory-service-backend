package com.finalProject.inventry_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;

@Component
public class JwtUtil {

    private final Key SECRET_KEY = Keys.hmacShaKeyFor("your_secret_key".getBytes(StandardCharsets.UTF_8)); // Replace with your actual secret key

    public Claims extractClaims(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            // This catches SignatureException, ExpiredJwtException, MalformedJwtException, etc.
            return false;
        }
    }
}
