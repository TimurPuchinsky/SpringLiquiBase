package com.example.springliquidbase.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StringResultModel extends GeneralResultModel {

    public StringResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public StringResultModel(String success) {
        this.result = success;
    }

    private String result;
}
