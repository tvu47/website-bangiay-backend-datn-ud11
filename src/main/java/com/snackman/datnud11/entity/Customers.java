package com.snackman.datnud11.entity;

import com.snackman.datnud11.dto.request.CustomerRequest;
import com.snackman.datnud11.dto.request.CustomerRequest1;
import com.snackman.datnud11.dto.request.CustomerRequest2;
import com.snackman.datnud11.utils.DateUtils;
import com.snackman.datnud11.utils.TimeUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String email;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "status")
    private Boolean status;

    public String getCreateTimeFormat(){
        SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return fm.format(this.createTime);
    }

    public Customers(CustomerRequest1 customerRequest){
        this.id = customerRequest.getId();
        this.firstName = customerRequest.getFirstName();
        this.lastName = customerRequest.getLastName();
        this.phoneNumber = customerRequest.getPhone();
        this.gender = customerRequest.getGender();
        this.email = customerRequest.getEmail();
        this.address = customerRequest.getAddress();
        this.dateOfBirth = DateUtils.stringToDate( customerRequest.getDateOfBirth());
        this.status = true;
    }
    public Customers(CustomerRequest2 customerRequest){
        this.id = customerRequest.getId();
        this.firstName = customerRequest.getFirstName();
        this.lastName = customerRequest.getLastName();
        this.phoneNumber = customerRequest.getPhone();
        this.gender = customerRequest.getGender();
        this.email = customerRequest.getEmail();
        this.dateOfBirth = DateUtils.stringToDate( customerRequest.getDateOfBirth());
        this.status = true;
    }
}