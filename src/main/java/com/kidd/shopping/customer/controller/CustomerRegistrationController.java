package com.kidd.shopping.customer.controller;

import com.kidd.shopping.base.BaseCustomerController;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.response.ServerErrorResponse;
import com.kidd.shopping.customer.entity.request.CustomerRegisterRequest;
import com.kidd.shopping.customer.service.CustomerRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@Api(value = "Student Registration", description = "Đăng ký tài khoản SV", position = 1)
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerRegistrationController extends BaseCustomerController {
    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    @ApiOperation(value = "Đăng ký tài khoản Sinh Vien", response = Iterable.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Đăng ký tài khoản thành công", response = OkResponse.class),
            @ApiResponse(code = 409, message = "Email đã tồn tại"),
            @ApiResponse(code = 403, message = "Mật khẩu yêu cầu trên 6 kí tự"),
            @ApiResponse(code = 400, message = "Email không hợp lệ"),
            @ApiResponse(code = 400, message = "Trường không hợp lệ")
    })
    @PostMapping("/register")
    public Response register(@Valid @RequestBody CustomerRegisterRequest customerRegisterRequest,
                             WebRequest webRequest) {
        try {
            return customerRegistrationService.createNewStudent(customerRegisterRequest, webRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
    }
}
