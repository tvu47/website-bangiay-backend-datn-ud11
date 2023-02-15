package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Query(value = "select * from products where product_name like %:name%", nativeQuery = true)
    List<Products> findByName(@Param("name") String name);

    @Query(value = "select * from products where category_id = :categoryId", nativeQuery = true)
    List<Products> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = "select * from products where price between :min and :max", nativeQuery = true)
    List<Products> findByPriceRange(@Param("min") Double min, @Param("max") Double max);

}