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

    private Double price;

    private String description;

    private Long discountId;

    private String manufactureAddress;

    private Boolean status;

    public ProductDTO(Products products){
        this.id = products.getId();
        this.categoryId = products.getCategoryId();
        this.productName = products.getProductName();
        this.price = products.getPrice();
        this.description = products.getDescription();
        this.discountId = products.getDiscountId();
        this.manufactureAddress = products.getManufactureAddress();
        this.status = products.getStatus();
    }
}
