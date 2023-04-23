package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "address")
@Data
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "address" )
	private String address;

	@Column(name = "city")
	private String city;
}