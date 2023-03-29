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
    private Date startTime;
    private Date endTime;
    private Boolean status;

}
