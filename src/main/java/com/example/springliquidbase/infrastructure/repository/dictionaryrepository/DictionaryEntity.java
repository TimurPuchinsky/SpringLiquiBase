package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "'Dictionary'")
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryEntity {

    public final static String ID = "id";

    @Id
    @Column(name = ID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = LanguageEntity.ID)
    private LanguageEntity languageEntityFrom_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = LanguageEntity.ID)
    private LanguageEntity languageEntityTo_id;
}
