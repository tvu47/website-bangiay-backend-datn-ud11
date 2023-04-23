package com.snackman.datnud11.dto.request;

import com.snackman.datnud11.entity.Address;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest2 {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Address> address;
    private int gender;
    private String phone;
    private String email;
    private String createTime;
    private Boolean active;
    private String dateOfBirth;
    private String password;
}
