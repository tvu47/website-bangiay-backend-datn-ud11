package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "bill_details")
@Data
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id", nullable = false)
    private Long customerId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "bill_id")
    private Long billId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "cost", precision = 10, scale = 2)
    private Double cost;
    @Column(name = "saleprice", precision = 10, scale = 2)
    private Double saleprice;
    @Column(name = "value_discount", precision = 10, scale = 2)
    private Double valueDiscount;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "status")
    private Boolean status;
}