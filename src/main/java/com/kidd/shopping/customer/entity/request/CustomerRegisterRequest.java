package com.kidd.shopping.customer.entity.request;

import com.kidd.shopping.validation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel
public class CustomerRegisterRequest {
    @ApiModelProperty(notes = "Tên khach hang, not null, not empty", required = true, position = 1)
    @NotEmpty
    private String firstName;
    @ApiModelProperty(notes = "Họ khach hang, not null, not empty", required = true, position = 2)
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    private String username;
    @NotEmpty
    private String password;
    private int gender;
    @ApiModelProperty(notes = "SDT NOT NULL, NOT EMPTY", required = true, position = 4)
    @Phone(nullable = false)
    private String phone;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

