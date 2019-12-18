package com.kidd.shopping.product.repository;

import com.kidd.shopping.product.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SizeRepository extends JpaRepository<Size,Integer> {
    @Query("select s.id from Size s where s.name = ?1 and s.productCategory.id =?2")
    int getSizeIDByName(String name,long categoryID);

}
