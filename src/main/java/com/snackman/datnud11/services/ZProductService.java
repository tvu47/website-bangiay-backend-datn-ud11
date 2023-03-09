package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.responses.ProductsResponse;

import java.util.List;

public interface ZProductService {
    List<ProductsResponse> getAllProductResponses();

    List<ProductsResponse> findByProductId(Long id) throws Exception;

    List<ProductsResponse> formatProductToProductResponse(List<Products> productsList);
}
