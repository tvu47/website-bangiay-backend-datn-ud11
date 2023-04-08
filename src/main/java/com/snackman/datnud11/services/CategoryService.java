package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Category;
import com.snackman.datnud11.entity.Colors;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    void delete(Category category);

    void delete(Long id);

    Category findById(Long id) throws Exception;

    List<Category> findAll();

    Category findById(List<Category> categoryList, Long id) throws Exception;
}
