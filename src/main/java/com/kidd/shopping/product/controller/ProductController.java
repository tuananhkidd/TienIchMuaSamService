package com.kidd.shopping.product.controller;

import com.kidd.shopping.base.BaseController;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.response.ServerErrorResponse;
import com.kidd.shopping.product.entity.ProductCategory;
import com.kidd.shopping.product.service.ProductService;
import com.kidd.shopping.utils.Constant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
            baseResponse = productService.crawlProductProperty(categoryID,path);
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
            baseResponse = productService.crawlProduct(categoryID,path);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerErrorResponse();
        }
        return baseResponse;
    }
}
