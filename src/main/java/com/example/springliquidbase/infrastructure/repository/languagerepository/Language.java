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
@Table(name = "Language")
@AllArgsConstructor
@NoArgsConstructor
public class Language {

    @Id
    private UUID id;
    private String name;

}
