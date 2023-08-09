package com.example.springliquidbase.domain;

import com.example.springliquidbase.infrastructure.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateModel{

    private WordModel wordModelFrom;
    private WordModel wordModelTo;
    private DictionaryModel dictionary;
}
