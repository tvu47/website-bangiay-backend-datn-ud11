package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorsRepository extends JpaRepository<Colors, Long> {

    @Query(value = "select * from colors where product_id = :productId", nativeQuery = true)
    List<Colors> findByProductId(@Param("productId") Long productId);

    @Query(value = "select * from colors where name_color =:name", nativeQuery = true)
    List<Colors> findByColorName(@Param("name") String name);

}