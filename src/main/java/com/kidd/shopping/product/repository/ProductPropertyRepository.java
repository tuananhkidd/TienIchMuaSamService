package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPropertyRepository extends JpaRepository<ProductProperty,Integer> {
}
