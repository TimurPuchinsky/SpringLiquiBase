package com.example.springliquidbase.domain.translate;

import com.example.springliquidbase.domain.PageModel;

public class TranslatePageModel extends PageModel {

    private String nameFilter;

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }
}
