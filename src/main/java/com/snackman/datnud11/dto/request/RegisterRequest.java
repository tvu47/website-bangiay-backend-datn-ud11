package com.snackman.datnud11.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    private String phone;
    private String email;
    private String password;
    private Date dateOfBirth;
}
