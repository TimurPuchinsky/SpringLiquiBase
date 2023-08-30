package com.example.springliquidbase.domain.translate;

import com.example.springliquidbase.domain.common.PageModel;
import lombok.Getter;

import java.util.UUID;

public class TranslatePageModel extends PageModel {

    public String getDictionary() {
        return dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    private String dictionary;

}
