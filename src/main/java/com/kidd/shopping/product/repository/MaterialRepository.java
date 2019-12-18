package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material,Integer> {
}
