package com.example.springliquidbase;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter implements Filter {

    public JwtRequestFilter(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    private JwtGenerator jwtGenerator;

    @Override
    public void doFilter(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, javax.servlet.ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("Authorization");
        String login = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                login = jwtGenerator.getLogin(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("время жизни токена вышло");
                login = null;
            } catch (Exception e) {
                log.debug("ошибка при дешифровании токена");
                login = null;
            }
        }
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    login,
                    null);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}
