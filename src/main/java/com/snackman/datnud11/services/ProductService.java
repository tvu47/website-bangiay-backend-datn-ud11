package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.ProductDTO;
import com.snackman.datnud11.dto.ProductUpdateRequest;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.entity.Size;
import com.snackman.datnud11.responses.ProductManagerResponse;
import com.snackman.datnud11.responses.ProductResponse;
import com.snackman.datnud11.responses.ProductsResponse;
import org.apache.commons.math3.stat.descriptive.summary.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Products save(Products products);
    Products updateProduct(ProductUpdateRequest products);
    Products save(ProductDTO productDTO);
    void delete(Products products);

    void delete(Long id);

    Products findById(Long id) throws Exception;

    List<Products> findAll();

    List<Products> getBestSellProducts();

    List<Products> getNewestProducts();

    List<Products> findByName(String name);

    List<Products> findByCategoryId(Long categoryId);

    List<Products> findBySameCategory(Long categoryId, Long productId);

    List<Products> findByPriceRange(Double min, Double max);

    List<Products> getAllOrderByPrice(String typeSort);

    List<Products> getAllOrderByName(String typeSort);

    List<Products> searchProducts(Map<String, List<String>> params) throws Exception;

    List<ProductManagerResponse> findAllProductsManager() throws Exception;

    List<ProductResponse> listAllProductManager() throws Exception;

    Products findByNameInList(List<Products> list, String name) throws Exception;
}
