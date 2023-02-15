package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "role_user")
@Data
public class RoleUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "role_id", nullable = false)
	private Long roleId;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Column(name = "status")
	private boolean status;
	@Column(name = "create_time")
	private Date createTime;
}