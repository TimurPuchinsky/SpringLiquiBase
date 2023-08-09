package com.example.springliquidbase.infrastructure.repository.translaterepository;

import com.example.springliquidbase.infrastructure.BaseModel;
import com.example.springliquidbase.infrastructure.repository.wordrepository.Word;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.Dictionary;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Translate extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    private Word wordFrom;
    @ManyToOne(cascade = CascadeType.ALL)
    private Word wordTo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dictionary dictionary;
}
