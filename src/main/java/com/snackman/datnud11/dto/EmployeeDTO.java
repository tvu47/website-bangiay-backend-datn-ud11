package com.snackman.datnud11.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Date birthday;
    private boolean gender;
    private String phoneNumber;
    private String address;
    private String username;
    private String password;
    private Date createTime;
    private boolean status;
}
