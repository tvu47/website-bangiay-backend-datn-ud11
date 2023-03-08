package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Category;
import com.snackman.datnud11.entity.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, Long> {
    @Query(value = "select  * from discount where discount_id in(:ids)", nativeQuery = true)
    List<Discounts> getDiscountsByListId(@Param("ids") List<Long> discountListId);
}