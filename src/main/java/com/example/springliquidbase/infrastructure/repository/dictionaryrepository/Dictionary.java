package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

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
public class Dictionary extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    private Language languageFrom;
    @ManyToOne(cascade = CascadeType.ALL)
    private Language languageTo;
}
