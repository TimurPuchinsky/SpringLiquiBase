package com.example.springliquidbase.domain.word;

import com.example.springliquidbase.domain.common.PageModel;
import com.example.springliquidbase.domain.common.PageResultModel;

public class WordPageModel extends PageModel {

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    private String nameFilter;
}
