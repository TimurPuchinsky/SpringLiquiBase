package com.example.springliquidbase.domain.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel {

    public String issuer;
    public String audience;
    public String subject;
    public Date expiration;
    public String role;
    public UUID userId;
    public UUID sessionId;
}
