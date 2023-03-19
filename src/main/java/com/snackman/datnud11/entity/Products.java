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

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "content")
    private String content;

    @Column(name = "manufacture")
    private String manufactureAddress;

    @Column(name ="status")
    private Boolean status;

    public Products(ProductDTO productDTO){
        this.id = productDTO.getId();
        this.categoryId = productDTO.getCategoryId();
        this.productName = productDTO.getProductName();
        this.content = productDTO.getContent();
        this.manufactureAddress = productDTO.getManufactureAddress();
        this.status = productDTO.getStatus();
        this.materialId = productDTO.getMaterialId();
    }
}