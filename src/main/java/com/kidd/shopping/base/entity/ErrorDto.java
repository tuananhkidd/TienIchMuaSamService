package com.kidd.shopping.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ErrorDto {
    @ApiModelProperty(notes = "Đối tượng lỗi", position = 1)
    private String target;
    @ApiModelProperty(notes = "Yêu cầu", position = 2)
    private String required;

    public ErrorDto(String target, String required) {
        this.target = target;
        this.required = required;
    }

    public ErrorDto() {
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }
}
