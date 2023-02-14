package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "products")
@Data
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
    private double price;
    @Column(name = "description")
    private String description;
    @Column(name = "discount_id", nullable = false)
    private Long discountId;
    @Column(name = "manufacture_address")
    private String manufactureAddress;
    @Column(name ="status")
    private boolean status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Size> sizes;
}