package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "discounts")
@Data
public class Discounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id", nullable = false)
    private Long id;
    @Column(name = "discount_name")
    private String discountName;
    @Column(name = "start_discount_time")
    private Date startDiscountTime;
    @Column(name = "end_discount_time")
    private Date endDiscountTime;
    @Column(name = "value_discount")
    private int valueDiscount;
    @Column(name = "status_discount")
    private boolean statusDiscount;
}