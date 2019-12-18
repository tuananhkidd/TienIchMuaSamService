package com.kidd.shopping.auth.controller;

import com.kidd.shopping.auth.entity.request.UserRequest;
import com.kidd.shopping.auth.service.LoginService;
import com.kidd.shopping.base.BaseController;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.response.ServerErrorResponse;
import com.kidd.shopping.utils.Constant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Login", description = "Đăng nhập", position = 1)
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "Đăng nhập dành cho Customer", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Đăng nhập thành công"),
            @ApiResponse(code = 401, message = "Sai email hoặc mật khẩu"),
            @ApiResponse(code = 403, message = "Tài khoản chưa được kích hoạt")
    })
    @PostMapping("/login/customer")
    public Response loginAsCustomer(@RequestBody UserRequest userRequest) {
        Response response;
        try {
            response = loginService.login(userRequest.getUsername(), userRequest.getPassword(), Constant.CUSTOMER);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//

//    @ApiOperation(value = "Đăng nhập bằng facebook cho KH", response = Iterable.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Đăng nhập thành công", response = TokenOkResponseModel.class),
//            @ApiResponse(code = 401, message = "Xác mình người dùng thành công, nhưng chưa đầy đủ thông tin yêu cầu")
//    })
//    @PostMapping("/students/facebook")
//    public Response customerFacebookLogin(@RequestBody FacebookLoginBody facebookLoginBody) {
//        Response baseResponse;
//        try {
//            baseResponse = loginService.facebookLogin(facebookLoginBody.getAccessToken(), Constant.STUDENT);
//        } catch (Exception e) {
//            baseResponse = new ServerErrorResponse();
//        }
//        return baseResponse;
//    }
}
