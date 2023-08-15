package com.example.springliquidbase.infrastructure.repository.wordrepository;

import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "'Word'")
@AllArgsConstructor
@NoArgsConstructor
public class WordEntity {

    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String LANGUAGE_ID = "\"languageId\"";

    @Id
    @Column(name = ID)
    private UUID id;

    @Column(name = NAME)
    private String name;

    @Column(name = LANGUAGE_ID)
    private UUID languageId;
}