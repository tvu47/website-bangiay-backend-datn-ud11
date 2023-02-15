package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.SearchProducts;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.repo.ProductsRepository;
import com.snackman.datnud11.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

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

    @Override
    public List<Products> findByName(String name) {
        return this.repo.findByName(name);
    }

    @Override
    public List<Products> findByCategoryId(Long categoryId) {
        return this.repo.findByCategoryId(categoryId);
    }

    @Override
    public List<Products> findByPriceRange(Double min, Double max) {
        return this.repo.findByPriceRange(min,max);
    }

    @Override
    public List<Products> searchProducts(Map<String, List<String>> params) throws Exception{
        if(params.containsKey(SearchProducts.FIND_BY_PRODUCT_NAME.paramName)){
            String productName = params.get(SearchProducts.FIND_BY_PRODUCT_NAME.paramName).get(0);
            return this.findByName(productName);
        }

        if(params.containsKey(SearchProducts.FIND_BY_CATEGORY_ID.paramName)){
            Long categoryId = Long.parseLong(params.get(SearchProducts.FIND_BY_CATEGORY_ID.paramName).get(0));
            return this.findByCategoryId(categoryId);
        }

        if(params.containsKey(SearchProducts.FIND_BY_PRICE_RANGE.paramName)){
            List<String> range = params.get(SearchProducts.FIND_BY_PRICE_RANGE.paramName);
            return this.findByPriceRange(Double.parseDouble(range.get(0)), Double.parseDouble(range.get(1)));
        }

        throw new Exception("Not found products");
    }
}
