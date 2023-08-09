package com.example.springliquidbase.infrastructure.repository.wordrepository;

import com.example.springliquidbase.infrastructure.BaseModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.Language;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Word extends BaseModel {

    private String word;
    @ManyToOne(cascade = CascadeType.ALL)
    private Language language;
}
