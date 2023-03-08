package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.Category;
import com.snackman.datnud11.entity.Discounts;
import com.snackman.datnud11.entity.Materials;
import com.snackman.datnud11.entity.Products;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
    private Long id;

    private Category category;

    private Materials materials;

    private String productName;

    private Double price;

    private String content;

    private Discounts discounts;

    private String manufactureAddress;

    private Boolean status;

    public ProductsResponse(Products products){
        this.id = products.getId();
        this.productName = products.getProductName();
        this.price = products.getPrice();
        this.content = products.getContent();
        this.manufactureAddress = products.getManufactureAddress();
        this.status = products.getStatus();
    }
}
