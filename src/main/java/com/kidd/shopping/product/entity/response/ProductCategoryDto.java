package com.kidd.shopping.product.entity.response;

public class ProductCategoryDto {
    private long id;
    private String category;

    public ProductCategoryDto() {
    }

    public ProductCategoryDto(long id, String category) {
        this.id = id;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
