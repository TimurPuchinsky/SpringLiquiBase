package com.example.springliquidbase.domain.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResultModel extends GeneralResultModel{

    public LoginResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public LoginResultModel(String errorCode, String errorMessage, String accessToken, String refreshToken) {
        super(errorCode, errorMessage);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private String accessToken;
    private String refreshToken;
}
