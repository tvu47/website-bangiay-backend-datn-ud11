package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    @Query(value = "select * from size where product_id = :productId",nativeQuery = true)
    List<Size> findByProductId(@Param("productId") Long id);

    @Query(value = "select * from size where size_name = :name", nativeQuery = true)
    List<Size> findBySizeName(@Param("name") String name);
}