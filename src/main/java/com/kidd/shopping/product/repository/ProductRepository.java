package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
