package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

    @Query(value = "select * from image where product_id = :productId", nativeQuery = true)
    List<Images> findByProductId(@Param(value = "productId") Long productId);

    @Query(value = "select * from image where product_id = :productId LIMIT 1", nativeQuery = true)
    Images findByProductIdTop1(@Param(value = "productId") Long productId);

}