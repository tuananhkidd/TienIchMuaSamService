package com.kidd.shopping.product.controller;

import com.kidd.shopping.base.BaseController;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.response.ServerErrorResponse;
import com.kidd.shopping.product.entity.ProductCategory;
import com.kidd.shopping.product.entity.request.CategoryRequest;
import com.kidd.shopping.product.service.ProductCategoryService;
import com.kidd.shopping.utils.Constant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "Product Managemament", description = "Quan ly san pham", position = 1)
@RequestMapping("/api/products")
@CrossOrigin("**")
public class ProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService productCategoryService;



    @ApiOperation(value = "Lấy danh sách category", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lấy thông tin thành công", response = OkResponse.class)
    })
    @GetMapping("/category")
    public Response getListCategories(@ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + ProductCategory.CREATE_DATE)
                                   @RequestParam(value = "sortBy", defaultValue = ProductCategory.CREATE_DATE) String sortBy,
                                   @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
                                   @RequestParam(value = "sortType", defaultValue = "asc") String sortType,
                                   @ApiParam(name = "pageIndex", value = "index trang, mặc định là 0")
                                   @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                   @ApiParam(name = "pageSize", value = "Kích thước trang, mặc định và tối đa là " + Constant.MAX_PAGE_SIZE)
                                   @RequestParam(value = "pageSize", defaultValue = "" + Constant.MAX_PAGE_SIZE) Integer pageSize) {
        Response baseResponse;
        try {
            baseResponse = productCategoryService.getListCategories(sortBy, sortType, pageIndex, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
        return baseResponse;
    }


    @ApiOperation(value = "Lấy danh sách category", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lấy thông tin thành công", response = OkResponse.class)
    })
    @PostMapping("/category")
    public Response createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Response baseResponse;
        try {
            baseResponse = productCategoryService.createCategory(categoryRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
        return baseResponse;
    }



}
