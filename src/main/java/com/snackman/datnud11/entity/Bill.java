package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
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

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "status")
	private Integer status;

	@Column(name ="address")
	private String address;

	@Column(name ="email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "voucher_id")
	private Long voucherId;

	@Column(name = "discount")
	private Integer discount;

	public String getCreateTimeFormat(){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return format.format(this.createTime);
	}
}