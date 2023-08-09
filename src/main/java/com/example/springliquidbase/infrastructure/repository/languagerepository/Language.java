package com.example.springliquidbase.infrastructure.repository.languagerepository;

import com.example.springliquidbase.infrastructure.BaseModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Language extends BaseModel {

    private String language;
}
