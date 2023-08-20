package com.example.springliquidbase.infrastructure.repository.translaterepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Translate")
@AllArgsConstructor
@NoArgsConstructor
public class TranslateEntity {

    public final static String ID = "id";
    public final static String WORD_FROM_ID = "wordFromId";
    public final static String WORD_TO_ID = "wordToId";
    public final static String DICTIONARY_ID = "dictionaryId";

    @Id
    @Column(name = ID)
    private UUID id;

    @Column(name = WORD_FROM_ID)
    private UUID wordFromId;

    @Column(name = WORD_TO_ID)
    private UUID wordToId;

    @Column(name = DICTIONARY_ID)
    private UUID dictionaryId;
}
