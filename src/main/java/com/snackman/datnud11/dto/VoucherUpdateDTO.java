package com.snackman.datnud11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class VoucherUpdateDTO {
    private Long id;
    private String code;
    private Integer value;
    private Integer quantity;
    private Date startTime;
    private Date endTime;
    private Boolean status;
    private Boolean outOfDate;
}
