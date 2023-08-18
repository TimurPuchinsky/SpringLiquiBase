package com.example.springliquidbase.infrastructure.repository.userrepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

//@Entity
//@Setter
//@Getter
//@Table(name = "'User'")
//@AllArgsConstructor
//@NoArgsConstructor
public class UserEntity{

    private final static String ID = "id";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String EMAIL = "email";
    private final static String PHONE = "phone";
    private final static String ROLE = "role";
    private final static String CREATED = "created";

    @Id
    @Column(name = ID)
    private UUID id;
    @Column(name = LOGIN)
    private String login;
    @Column(name = PASSWORD)
    private String password;
    @Column(name = EMAIL)
    private String email;
    @Column(name = PHONE)
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = ROLE)
    private String role;
    @Column(name = CREATED)
    private LocalDateTime created;
}
