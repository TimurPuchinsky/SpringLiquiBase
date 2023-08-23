package com.example.springliquidbase.domain.usersession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionModel {

    public UUID session_id;
    public String access_token;
    public LocalDateTime access_token_expired;
    public String refresh_token;
    public LocalDateTime refresh_token_expired;
    public UUID user_id;
    public LocalDateTime created;
}
