package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "product_detail")
@Data
@NoArgsConstructor
public class ProductDetail {
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
    @Column(name = "image")
    private String image;

    public ProductDetail(InventoryImportExcelDTO inventory) {
        this.id = inventory.getProductDatailId();
        this.productId = inventory.getProductId();
        this.sku = inventory.getSku();
        this.color = inventory.getColor();
        this.size = inventory.getSize();
        this.quatity = inventory.getQuantity();
        this.price = inventory.getPrice();
        this.image = inventory.getImage();
        this.importTime = new Date();
        this.status = true;
    }
}