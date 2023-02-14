package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "role_employee")
@Data
public class RoleEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", nullable = false)
	private Long id;
	@Column(name = "employee_id", nullable = false)
	private Long employeeId;
	@Column(name = "status")
	private boolean status;
	@Column(name = "create_time")
	private Date createTime;
}