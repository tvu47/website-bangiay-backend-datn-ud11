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
    @Column(name = "amount_value", precision = 10, scale = 2)
    private double amount;
    @Column(name = "price", precision = 10, scale = 2)
    private double price;
    @Column(name = "import_time")
    private Date importTime;
    @Column(name ="brand")
    private String brand;
    @Column(name ="status")
    private boolean status;
}