package com.example.springliquidbase.domain;

import com.example.springliquidbase.domain.dictionary.DictionaryModel;

import java.util.Collection;
import java.util.List;

public class PageResultModel<T> {

    private int totalCount;
    private Collection<T> list;

    public PageResultModel(int totalCount, Collection<T> models) {
        this.totalCount = totalCount;
        this.list = models;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Collection<T> getList() {
        return list;
    }

    public void setList(Collection<T> list) {
        this.list = list;
    }
}
