package com.snackman.datnud11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String gender; //Nam or Nữ
    private String address;
    private String password;
    private String repassword;
    private String role;

}
