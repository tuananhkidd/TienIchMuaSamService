package com.kidd.shopping.auth.entity;

import io.swagger.annotations.ApiModelProperty;

public class LoginResult {
    @ApiModelProperty(notes = "ID")
    private String id;
    @ApiModelProperty(notes = "Access token", position = 1)
    private String accessToken;
    @ApiModelProperty(notes = "Refresh token, dùng để cấp lại access token khi hết hạn", position = 2)
    private String refreshToken;
    @ApiModelProperty(notes = "Thời gian access token hết hạn", position = 3)
    private Long expireIn;

    public LoginResult(String id,
                       String accessToken,
                       String refreshToken,
                       Long expireIn) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireIn = expireIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }
}
