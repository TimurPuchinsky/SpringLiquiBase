package com.example.springliquidbase.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateModel {

    private String login;
    private String password;
    private String email;
    private String surname;
    private String name;
    private String father;
    private String phone;
}
