package com.kidd.shopping.product.service;

import com.kidd.shopping.base.entity.PageDto;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.tools.PageAndSortRequestBuilder;
import com.kidd.shopping.product.entity.ProductCategory;
import com.kidd.shopping.product.entity.request.CategoryRequest;
import com.kidd.shopping.product.repository.ProductCategoryRepository;
import com.kidd.shopping.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public Response getListCategories(String sortBy, String sortType, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
        return new OkResponse(new PageDto<>(productCategoryRepository.getListCategories(pageable)));
    }

    public Response createCategory(CategoryRequest categoryRequest) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory(categoryRequest.getCategory());

        productCategoryRepository.save(productCategory);
        return new OkResponse();
    }
}
