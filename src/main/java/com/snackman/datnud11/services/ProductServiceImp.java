package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{

    @Autowired
    private ProductsRepository repo;

    @Override
    public Products save(Products products) {
        return this.repo.save(products);
    }

    @Override
    public void delete(Products products) {
        this.repo.delete(products);
    }

    @Override
    public void delete(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public Products findById(Long id) throws Exception {
        Optional<Products> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Not found product id " + id);
        }
        return optional.get();
    }

    @Override
    public List<Products> findAll() {
        return this.repo.findAll();
    }
}
