package com.example.springliquidbase.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private UUID id;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime created;

}
