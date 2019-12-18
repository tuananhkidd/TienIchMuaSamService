package com.kidd.shopping.product.entity.request;

import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@ApiModel
public class CategoryRequest {
    @NotNull
    @NotEmpty
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
