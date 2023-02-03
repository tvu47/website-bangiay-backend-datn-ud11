package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "size")
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "size_name")
    private String sizeName;
    @Column(name ="active_status")
    private boolean activeStatus;
}