package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "bill")
@Data
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_id", nullable = false)
	private Long id;
	@Column(name = "customer_id", nullable = false)
	private Long customerId;
	@Column(name = "name")
	private String name;
	@Column(name = "amount", precision = 10, scale = 2)
	private double amount;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "payment_status")
	private boolean paymentStatus;
}