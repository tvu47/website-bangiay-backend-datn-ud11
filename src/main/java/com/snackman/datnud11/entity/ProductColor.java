package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "product_color")
@Data
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "color_id", nullable = false)
    private Long colorId;
    @Column(name ="status")
    private boolean status;
}