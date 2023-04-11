package com.snackman.datnud11.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherResponse {

    private Long id;
    private String code;
    private Integer value;
    private Integer quantity;
    private String startTime;
    private String endTime;
    private Boolean status;
    private Boolean outOfDate;

}
