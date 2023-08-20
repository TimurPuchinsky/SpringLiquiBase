package com.example.springliquidbase.infrastructure.repository.userrepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    public final static String ID = "id";
    public final static String LOGIN = "login";
    public final static String PASSWORD = "password";
    public final static String EMAIL = "email";
    public final static String PHONE = "phone";
    public final static String ROLE = "role";
    public final static String CREATED = "created";

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
    @Column(name = ROLE)
    private String role;
    @Column(name = CREATED)
    private LocalDateTime created;
}
