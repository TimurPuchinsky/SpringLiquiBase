package com.example.springliquidbase.infrastructure.repository.wordrepository;

import com.example.springliquidbase.infrastructure.repository.languagerepository.Language;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Language> language_id;
}
