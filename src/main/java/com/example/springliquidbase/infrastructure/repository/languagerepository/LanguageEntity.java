package com.example.springliquidbase.infrastructure.repository.languagerepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "'Language'")
@AllArgsConstructor
@NoArgsConstructor
public class LanguageEntity {

    public final static String ID = "id";
    public final static String NAME = "name";

    @Id
    @Column(name = ID)
    private UUID id;

    @Column(name = NAME)
    private String name;
}
