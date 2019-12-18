package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.ProductCategory;
import com.kidd.shopping.product.entity.response.ProductCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
    @Query("select new com.kidd.shopping.product.entity.response.ProductCategoryDto(pc.id,pc.category) from ProductCategory pc ")
    Page<ProductCategoryDto> getListCategories(Pageable pageable);
}
