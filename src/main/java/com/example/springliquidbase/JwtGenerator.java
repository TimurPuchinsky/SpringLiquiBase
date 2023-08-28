package com.example.springliquidbase;

import com.example.springliquidbase.domain.user.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtGenerator {

    @Value("${spring.jwt.datasource.secretkey}")
    private String SECRET_KEY;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserModel userModel, UUID session_id) {
        Map<String, Object> role = new HashMap<>();
        role.put("role", userModel.getRole());
        return Jwts
                .builder()
                .setIssuer("api")
                .setAudience("app")
                .setSubject(userModel.getLogin())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .addClaims(role)
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

    public List<String> getRoles(String token) {
        Object roleClaim = getAllClaimsFromToken(token).get("role");
        if (roleClaim instanceof List) {
            return (List<String>) roleClaim;
        } else if (roleClaim instanceof String) {
            return Arrays.asList((String) roleClaim);
        } else {
            return Collections.emptyList();
        }
    }
}
