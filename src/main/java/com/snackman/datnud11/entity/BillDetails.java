package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "bill_details")
@NoArgsConstructor
@Data
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id", nullable = false)
    private Long billDetailId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;
    @Column(name= "color_name")
    private String colorName;
    @Column(name = "size_name")
    private String sizeName;
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

    @Column(name="color")
    private Long color;
    @Column(name="size")
    private Long size;
}