package com.example.springliquidbase.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuccessResultModel extends GeneralResultModel {

    public SuccessResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        success = false;
    }

    public SuccessResultModel(boolean success) {
        this.success = success;
    }

    private boolean success;
}
