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

    @Id
    @Column(name = ID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = WordEntity.ID)
    private WordEntity wordEntityFrom_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = WordEntity.ID)
    private WordEntity wordEntityTo_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = DictionaryEntity.ID)
    private DictionaryEntity dictionary_Entity_id;
}
