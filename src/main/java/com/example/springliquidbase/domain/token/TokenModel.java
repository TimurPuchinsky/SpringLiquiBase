package com.example.springliquidbase.domain.token;

import com.example.springliquidbase.domain.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel {

    public String issuer;
    public String audience;
    public String subject;
    public Date expiration;
    public List<String> role;
    public UUID userId;
    public UUID sessionId;
}
