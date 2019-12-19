package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.Product;
import com.kidd.shopping.product.entity.response.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("select new com.kidd.shopping.product.entity.response.ProductDto(" +
            "p.id,p.name,p.regularPrice,p.sellerPrice,p.avatarUrl,p.favouriteCount) from Product p")
    Page<ProductDto> getListProducts(Pageable pageable);

    @Query("select new com.kidd.shopping.product.entity.response.ProductDto(" +
            "p.id,p.name,p.regularPrice,p.sellerPrice,p.avatarUrl,p.favouriteCount) " +
            "from Product p join p.productCategory pc" +
            " where  pc.id = :category_id")
    Page<ProductDto> getListProductsByCategory(Pageable pageable,@Param("category_id") Long category);
}
