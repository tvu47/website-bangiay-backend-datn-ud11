package com.snackman.datnud11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherDTO {

    private String code;
    private Integer value;
    private Integer quantity;
    private Date startTime;
    private Date endTime;

}
