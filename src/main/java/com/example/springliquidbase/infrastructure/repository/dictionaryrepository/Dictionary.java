package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

import com.example.springliquidbase.infrastructure.repository.languagerepository.Language;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Table(name = "Dictionary")
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary {

    @Id
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Language> languageFrom_id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Language> languageTo_id;
}
