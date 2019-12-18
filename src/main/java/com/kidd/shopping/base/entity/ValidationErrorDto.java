package com.kidd.shopping.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel
public class ValidationErrorDto {
    @ApiModelProperty(notes = "Danh sách các lỗi")
    private List<ErrorDto> fieldErrors = new ArrayList<>();

    public ValidationErrorDto() {
    }

    public List<ErrorDto> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<ErrorDto> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public void addError(String field, String msg) {
        fieldErrors.add(new ErrorDto(field, msg));
    }
}
