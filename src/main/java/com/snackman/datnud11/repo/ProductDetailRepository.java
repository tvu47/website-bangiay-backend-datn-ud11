package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Query(value = "select * from product_detail where product_id = :id", nativeQuery = true)
    List<ProductDetail> findByProductId(@Param("id") Long id);

    @Query(value = "select * from product_detail where sku = :sku", nativeQuery = true)
    ProductDetail findBySku(@Param("sku") String sku);

}