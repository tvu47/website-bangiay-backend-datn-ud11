package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    private Long id;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "quantity")
    private int quatity;
    @Column(name = "price", precision = 10, scale = 2)
    private double price;
    @Column(name = "import_date")
    private Date importTime;
    @Column(name ="status")
    private boolean status;

    @Column(name = "image")
    private String image;

    @Column(name = "sku")
    private String sku;
    @Column(name = "color")
    private Long color;
    @Column(name = "size")
    private Long size;
    @Column(name = "color_name")
    private String colorName;
    @Column(name = "size_name")
    private String sizeName;
}