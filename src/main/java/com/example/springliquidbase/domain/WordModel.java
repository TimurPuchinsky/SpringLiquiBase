package com.example.springliquidbase.domain;

import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordModel {

    private UUID id;
    private String name;
    private UUID language_Entity_id;
}
