package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.entity.Size;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Products save(Products products);

    void delete(Products products);

    void delete(Long id);

    Products findById(Long id) throws Exception;

    List<Products> findAll();

    List<Products> findByName(String name);

    List<Products> findByCategoryId(Long categoryId);

    List<Products> findByPriceRange(Double min, Double max);

    List<Products> searchProducts(Map<String, List<String>> params) throws Exception;
}
