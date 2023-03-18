package com.snackman.datnud11.dto;

import com.snackman.datnud11.entity.Products;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private Long categoryId;

    private String productName;

    private String content;

    private String manufactureAddress;

    private Boolean status;

    private Long materialId;

    public ProductDTO(Products products){
        this.id = products.getId();
        this.categoryId = products.getCategoryId();
        this.productName = products.getProductName();
        this.content = products.getContent();
        this.manufactureAddress = products.getManufactureAddress();
        this.status = products.getStatus();
        this.materialId = products.getMaterialId();
    }
}
