package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "customers")
@Data
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    @Email(message = "email must not be invalid")
    private String email;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private Boolean status;

    public String getCreateTimeFormat(){
        SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return fm.format(this.createTime);
    }
}