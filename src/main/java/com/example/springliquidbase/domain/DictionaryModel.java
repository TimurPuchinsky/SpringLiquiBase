package com.example.springliquidbase.domain;

import com.example.springliquidbase.infrastructure.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryModel {

    private LanguageModel languageFrom;
    private LanguageModel languageTo;
}
