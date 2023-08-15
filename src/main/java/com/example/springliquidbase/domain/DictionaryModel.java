package com.example.springliquidbase.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryModel {

    private UUID id;
    private UUID languageFrom;
    private UUID languageTo;
    private String name;
}
