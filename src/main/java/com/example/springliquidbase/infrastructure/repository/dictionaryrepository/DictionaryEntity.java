package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

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
@Table(name = "'Dictionary'")
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryEntity {

    public final static String ID = "id";
    public final static String LANGUAGEFROM_ID = "\"languageFromId\"";
    public final static String LANGUAGETO_ID = "\"languageToId\"";
    public final static String NAME = "name";

    @Id
    @Column(name = ID)
    private UUID id;

    @Column(name = LANGUAGEFROM_ID)
    private UUID languageFromId;

    @Column(name = LANGUAGETO_ID)
    private UUID languageToId;

    @Column(name = NAME)
    private String name;
}
