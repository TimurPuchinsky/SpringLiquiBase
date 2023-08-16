package com.example.springliquidbase.domain.word;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordModel {

    private UUID id;
    private String name;
    private UUID languageId;
}
