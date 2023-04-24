package com.snackman.datnud11.entity;

import com.snackman.datnud11.dto.request.EmployeeRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name" )
    private String lastName;

    @Column(name = "birdthday")
    private Date birthday;
    @Column(name = "gender" )
    private int gender;
    @Column(name = "email" )
    private String email;
    @Column(name = "address" )
    private String address;
    @Column(name = "phone_number" )
    private String phoneNumber;
    @Column(name = "username" )
    private String username;
    @Column(name = "create_time" )
    private Date createTime;
    @Column(name = "status" )
    private int status;

    public Employee(EmployeeRequest employeeRequest) {
        this.id = employeeRequest.getId();
        this.firstName= employeeRequest.getFirstName();
        this.lastName = employeeRequest.getLastName();
        this.birthday = employeeRequest.getBirthday();
        this.gender = employeeRequest.getGender();
        this.email = employeeRequest.getEmail();
        this.address = employeeRequest.getAddress();
        this.username = employeeRequest.getUsername();
        this.phoneNumber = employeeRequest.getPhoneNumber();
        this.createTime = employeeRequest.getCreateTime();
        this.status = employeeRequest.getStatus();
    }
}
