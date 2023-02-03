package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payment_type")
@Data
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;
    @Column(name = "payment_name")
    private String materialName;
    @Column(name ="status")
    private boolean status;
}