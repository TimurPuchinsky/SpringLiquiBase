package com.example.springliquidbase.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateModel{

    @Id
    private UUID id;
    private WordModel wordModelFrom;
    private WordModel wordModelTo;
    private DictionaryModel dictionary;
}
