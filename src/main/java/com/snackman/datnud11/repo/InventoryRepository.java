package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "select * from inventory where product_id = :id", nativeQuery = true)
    List<Inventory> findByProductId(@Param("id") Long id);

}