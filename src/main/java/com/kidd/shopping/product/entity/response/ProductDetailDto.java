package com.kidd.shopping.product.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.List;

@ApiModel
public class ProductDetailDto extends ProductDto {
    private long stock;
    private String description;
    @JsonProperty("image_urls")
    private List<String> imageUrls;
    @JsonProperty("category_id")
    private long categoryID;
    private int rating;

    public ProductDetailDto(String id,
                            String name,
                            int favouriteCount,
                            BigDecimal regularPrice,
                            BigDecimal sellerPrice,
                            long stock,
                            String description,
                            List<String> imageUrls,
                            String avatarUrl,
                            long categoryID,
                            int rating) {
        super(id, name, regularPrice, sellerPrice, avatarUrl, favouriteCount);
        this.stock = stock;
        this.description = description;
        this.imageUrls = imageUrls;
        this.categoryID = categoryID;
        this.rating = rating;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
