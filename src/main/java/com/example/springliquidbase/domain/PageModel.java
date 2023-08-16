package com.example.springliquidbase.domain;

import io.ebean.annotation.NotNull;

public class PageModel {

    @NotNull
    private Integer pageNum;

    @NotNull
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
