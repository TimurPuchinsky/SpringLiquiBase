package com.example.springliquidbase.domain.user;

import com.example.springliquidbase.domain.common.PageModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserPageModel extends PageModel {

    private String login;
    private String password;
    private String email;
    private String surname;
    private String name;
    private String father;
    private String phone;
    private String role;
}
