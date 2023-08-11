package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Table(name = "Dictionary")
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary {

    @Id
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    private LanguageEntity languageEntityFrom_id;
    @ManyToOne(cascade = CascadeType.ALL)
    private LanguageEntity languageEntityTo_id;
}
