package com.snackman.datnud11.responses;

import lombok.Data;

@Data
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private String phone;
    private String email;
    private String createTime;
    private Boolean active;

}
