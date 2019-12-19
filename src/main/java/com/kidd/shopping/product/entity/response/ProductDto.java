package com.kidd.shopping.product.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class ProductDto {
    private String id;
    private String name;
    @JsonProperty("favourite_count")
    private int favouriteCount;
    @JsonProperty("regular_price")
    private BigDecimal regularPrice;
    @JsonProperty("seller_price")
    private BigDecimal sellerPrice;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    public ProductDto() {
    }

    public ProductDto(String id,
                      String name,
                      BigDecimal regularPrice,
                      BigDecimal sellerPrice,
                      String avatarUrl,
                      int favouriteCount) {
        this.id = id;
        this.name = name;
        this.regularPrice = regularPrice;
        this.sellerPrice = sellerPrice;
        this.avatarUrl = avatarUrl;
        this.favouriteCount = favouriteCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(int favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getSellerPrice() {
        return sellerPrice;
    }

    public void setSellerPrice(BigDecimal sellerPrice) {
        this.sellerPrice = sellerPrice;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
