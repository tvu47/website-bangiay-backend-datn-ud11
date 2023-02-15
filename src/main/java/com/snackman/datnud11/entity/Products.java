package com.snackman.datnud11.entity;

import com.snackman.datnud11.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price", precision = 10, scale = 2)
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_id", nullable = false)
    private Long discountId;

    @Column(name = "manufacture_address")
    private String manufactureAddress;

    @Column(name ="status")
    private Boolean status;

    public Products(ProductDTO productDTO){
        this.id = productDTO.getId();
        this.categoryId = productDTO.getCategoryId();
        this.productName = productDTO.getProductName();
        this.price = productDTO.getPrice();
        this.description = productDTO.getDescription();
        this.discountId = productDTO.getDiscountId();
        this.manufactureAddress = productDTO.getManufactureAddress();
        this.status = productDTO.getStatus();
    }
}