package com.example.springliquidbase.domain.dictionary;

import com.example.springliquidbase.domain.PageModel;

public class DictionaryPageModel extends PageModel {

    private String nameFilter;

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }
}
