package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColorRepository extends JpaRepository<Color,Integer> {
    @Query("select c.id from Color c where c.dataFilter = ?1")
    int getColorIDByByDataFilter(int dataFilter);
}
