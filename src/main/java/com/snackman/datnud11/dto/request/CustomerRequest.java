package com.snackman.datnud11.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private String phone;
    private String email;
    private String createTime;
    private Boolean active;
    private Date dateOfBirth;
}
