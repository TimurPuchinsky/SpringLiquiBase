package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.token.TokenModel;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

public class CommonUtils {

    public static List<String> getRole() {
        var user = (TokenModel) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return user.getRole();
    }

    public static String getIssuer() {
        var user = (TokenModel) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return user.getIssuer();
    }

    public static String getSubject() {
        try {
            var tcc = (TokenModel) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            return tcc.getSubject();
        } catch (MalformedJwtException | ClassCastException e) {
            return null;
        }
    }

    public static UUID getSessionId() {
        var sessionId = (TokenModel) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return sessionId.getSessionId();
    }

    public static UUID getUserId() {
        var userId = (TokenModel) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return userId.getUserId();
    }
}
