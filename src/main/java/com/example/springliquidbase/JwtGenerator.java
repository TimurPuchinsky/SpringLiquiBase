package com.example.springliquidbase;

import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.user.role.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtGenerator {

    @Value("${spring.jwt.datasource.secretkey}")
    private String SECRET_KEY;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserModel userModel, UUID session_id) {
        return Jwts
                .builder()
                .setIssuer("api")
                .setAudience("app")
                .setSubject(userModel.getLogin())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .claim("role", Role.USER.name())
                .claim("userId", userModel.getId())
                .claim("sessionId", session_id)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserModel userModel, UUID session_id) {
        return Jwts
                .builder()
                .setIssuer("api")
                .setAudience("app")
                .setSubject(userModel.getLogin())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("role", "REFRESH_TOKEN")
                .claim("userId", userModel.getId())
                .claim("sessionId", session_id)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getLogin(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
