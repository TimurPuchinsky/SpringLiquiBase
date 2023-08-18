package com.example.springliquidbase.domain.translate;

import com.example.springliquidbase.domain.common.PageModel;
import lombok.Getter;

public class TranslatePageModel extends PageModel {

    private String languageFrom;
    private String languageTo;

    public String getLanguageFrom() {
        return languageFrom;
    }

    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }

    public String getLanguageTo() {
        return languageTo;
    }

    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }
}
