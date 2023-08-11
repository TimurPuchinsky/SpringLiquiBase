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
    @ManyToOne(cascade = CascadeType.ALL)
    private Word wordFrom_id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Word wordTo_id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dictionary dictionary_id;
}
