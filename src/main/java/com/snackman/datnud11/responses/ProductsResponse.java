package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.*;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
    private Long id;

    private Category category;

    private Materials materials;

    private String productName;
    private String content;

    private Discounts discounts;

    private String manufactureAddress;

    private Boolean status;

    private List<Images> images;

    private List<Size> sizes;

    private List<Colors> colors;

    public ProductsResponse(Products products){
        this.id = products.getId();
        this.productName = products.getProductName();
        this.content = products.getContent();
        this.manufactureAddress = products.getManufactureAddress();
        this.status = products.getStatus();
    }
}
