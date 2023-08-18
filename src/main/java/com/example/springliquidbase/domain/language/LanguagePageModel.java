package com.example.springliquidbase.domain.language;

import com.example.springliquidbase.domain.common.PageModel;

public class LanguagePageModel extends PageModel {

    private String nameFilter;

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }
}
