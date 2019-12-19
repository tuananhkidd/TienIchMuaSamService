package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.ProductPropertyColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPropertyColorRepository extends JpaRepository<ProductPropertyColor,Integer> {
    List<ProductPropertyColor> findAllByProductProperty_Id(int productPropertyID);
}
