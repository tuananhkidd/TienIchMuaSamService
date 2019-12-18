package com.kidd.shopping.base.entity;

public class TokenExpirationDto {
    private String error;
    private String error_description;

    public TokenExpirationDto(String refreshToken) {
        this.error = "invalid_token";
        this.error_description = "Access token expired: " + refreshToken;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
