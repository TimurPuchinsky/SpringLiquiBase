package com.example.springliquidbase.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryModel {

    @Id
    private UUID id;
    private LanguageModel languageFrom;
    private LanguageModel languageTo;
}
