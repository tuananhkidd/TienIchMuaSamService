package com.kidd.shopping.product.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel
public class ProductFilter {
    @JsonProperty("size_id")
    private Long sizeID;
    @JsonProperty("color_id")
    private Long colorID;
    @JsonProperty("material_id")
    private Long materialID;
    @JsonProperty("style_id")
    private Long styleID;
    private String keyword;
    private long createdDate;
    @JsonProperty("category_id")
    private Long categoryId;

    public Long getSizeID() {
        return sizeID;
    }

    public void setSizeID(Long sizeID) {
        this.sizeID = sizeID;
    }

    public Long getColorID() {
        return colorID;
    }

    public void setColorID(Long colorID) {
        this.colorID = colorID;
    }

    public Long getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Long materialID) {
        this.materialID = materialID;
    }

    public Long getStyleID() {
        return styleID;
    }

    public void setStyleID(Long styleID) {
        this.styleID = styleID;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
