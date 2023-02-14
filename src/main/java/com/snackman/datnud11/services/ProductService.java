package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.entity.Size;

import java.util.List;

public interface ProductService {

    Products save(Products products);

    void delete(Products products);

    void delete(Long id);

    Products findById(Long id) throws Exception;

    List<Products> findAll();
}
