package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "Dictionary")
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryEntity {

    public final static String ID = "id";
    public final static String LANGUAGE_FROM_ID = "languageFromId";
    public final static String LANGUAGE_TO_ID = "languageToId";
    public final static String NAME = "name";

    @Id
    @Column(name = ID)
    private UUID id;

    @Column(name = LANGUAGE_FROM_ID)
    private UUID languageFromId;

    @Column(name = LANGUAGE_TO_ID)
    private UUID languageToId;

    @Column(name = NAME)
    private String name;
}
