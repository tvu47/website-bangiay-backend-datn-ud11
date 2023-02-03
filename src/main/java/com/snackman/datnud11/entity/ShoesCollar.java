package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shoes_collar")
@Data
public class ShoesCollar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoes_collar_id", nullable = false)
    private Long id;
    @Column(name = "name_shoes_collar")
    private String shoesCollarName;
    @Column(name = "status")
    private boolean status;
}