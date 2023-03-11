package com.snackman.datnud11.responses;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientInformationResponse {
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private boolean gender;
    private String phoneNumber;
    private String address;
    private Date createTime;
    private String token;
}
