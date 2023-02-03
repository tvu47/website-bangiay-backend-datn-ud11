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
    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(name = "quatity")
    private int quatity;
    @Column(name = "amount", precision = 10, scale = 2)
    private double amount;
    @Column(name = "cost", precision = 10, scale = 2)
    private double cost;
    @Column(name = "saleprice", precision = 10, scale = 2)
    private double saleprice;
    @Column(name = "value_discount", precision = 10, scale = 2)
    private double valueDiscount;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "payment_status")
    private boolean payment_status;
}