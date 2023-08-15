package com.example.springliquidbase.infrastructure.repository.translaterepository;

import com.example.springliquidbase.infrastructure.repository.wordrepository.WordEntity;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.DictionaryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "'Translate'")
@AllArgsConstructor
@NoArgsConstructor
public class TranslateEntity {

    public final static String ID = "id";
    public final static String WORDFROMID = "\"wordFromId\"";
    public final static String WORDTOID = "\"wordToId\"";
    public final static String DICTIONARYID = "\"dictionaryId\"";

    @Id
    @Column(name = ID)
    private UUID id;

    @Column(name = WORDFROMID)
    private UUID wordFromId;

    @Column(name = WORDTOID)
    private UUID wordToId;

    @Column(name = DICTIONARYID)
    private UUID dictionaryId;
}
