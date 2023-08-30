package com.example.springliquidbase.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullnameModel {

    private String surname;
    private String name;
    private String father;
}
