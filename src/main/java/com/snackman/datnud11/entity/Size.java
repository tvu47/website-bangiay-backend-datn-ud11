package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "size")
@Data
@ToString
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "size_name")
    private String sizeName;

    @Column(name = "active_status")
    private boolean activeStatus;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Products product;
}