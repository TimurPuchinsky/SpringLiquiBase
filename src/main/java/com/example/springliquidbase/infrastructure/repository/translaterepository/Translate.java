package com.example.springliquidbase.infrastructure.repository.translaterepository;

import com.example.springliquidbase.infrastructure.repository.wordrepository.Word;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.Dictionary;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Translate")
@AllArgsConstructor
@NoArgsConstructor
public class Translate{

    @Id
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Word> wordFrom_id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Word> wordTo_id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Dictionary> dictionary_id;
}
