package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select  * from category where category_id in(:ids)", nativeQuery = true)
    List<Category> getCategoryByListId(@Param("ids") List<Long> categoryListId);
    Optional<Category> findCategoryByCategoryName(String name);
}