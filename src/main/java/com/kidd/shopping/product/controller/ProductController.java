package com.kidd.shopping.product.controller;

import com.kidd.shopping.base.BaseController;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.response.ServerErrorResponse;
import com.kidd.shopping.product.entity.Product;
import com.kidd.shopping.product.entity.request.ProductFilter;
import com.kidd.shopping.product.service.ProductService;
import com.kidd.shopping.utils.Constant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Product Managemament", description = "Quan ly san pham", position = 1)
@RequestMapping("/api/products")
@CrossOrigin("**")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tạo thành công", response = OkResponse.class)
    })
    @PostMapping("/property/{cat_id}")
    public Response crawlProductProperty(@PathVariable(name = "cat_id") long categoryID,
                                         @RequestParam("path") String path) {
        Response baseResponse;
        try {
            baseResponse = productService.crawlProductProperty(categoryID, path);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
        return baseResponse;
    }


    @ApiOperation(value = "", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tạo thành công", response = OkResponse.class)
    })
    @PostMapping("/{cat_id}")
    public Response crawlProduct(@PathVariable(name = "cat_id") long categoryID,
                                 @RequestParam("path") String path) {
        Response baseResponse;
        try {
            baseResponse = productService.crawlProduct(categoryID, path);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
        return baseResponse;
    }

    @ApiOperation(value = "", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tạo thành công", response = OkResponse.class)
    })
    @GetMapping
    public Response getListProduct(@RequestParam("category_id") Long categoryId,
                                   @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Product.CREATE_DATE)
                                   @RequestParam(value = "sortBy", defaultValue = Product.CREATE_DATE) String sortBy,
                                   @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là asc")
                                   @RequestParam(value = "sortType", defaultValue = "asc") String sortType,
                                   @ApiParam(name = "pageIndex", value = "index trang, mặc định là 0")
                                   @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                   @ApiParam(name = "pageSize", value = "Kích thước trang, mặc định và tối đa là " + Constant.MAX_PAGE_SIZE)
                                   @RequestParam(value = "pageSize", defaultValue = "" + Constant.MAX_PAGE_SIZE) Integer pageSize) {
        Response baseResponse;
        try {
            baseResponse = productService.getListProduct(categoryId,sortBy, sortType, pageIndex, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
        return baseResponse;
    }

    @ApiOperation(value = "", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tạo thành công", response = OkResponse.class)
    })
    @GetMapping("/search")
    public Response searchListProduct(
            @ModelAttribute ProductFilter productFilter,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Product.CREATE_DATE)
            @RequestParam(value = "sortBy", defaultValue = Product.CREATE_DATE) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là asc")
            @RequestParam(value = "sortType", defaultValue = "asc") String sortType,
            @ApiParam(name = "pageIndex", value = "index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc định và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", defaultValue = "" + Constant.MAX_PAGE_SIZE) Integer pageSize) {
        Response baseResponse;
        try {
            baseResponse = productService.searchProducts(productFilter,sortBy, sortType, pageIndex, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
        return baseResponse;
    }
}
