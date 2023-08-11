package com.example.springliquidbase.infrastructure.repository.wordrepository;

import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Table(name = "Word")
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    private UUID id;
    private String word;
    @ManyToOne(cascade = CascadeType.ALL)
    private LanguageEntity language_Entity_id;
}
