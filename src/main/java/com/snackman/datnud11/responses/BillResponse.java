package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.BillDetails;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BillResponse {

    private Long id;
    private String customerName;
    private Integer status;
    private String email;
    private String phone;
    private String address;
    private String createTime;

    private List<BillDetails> details;
}
