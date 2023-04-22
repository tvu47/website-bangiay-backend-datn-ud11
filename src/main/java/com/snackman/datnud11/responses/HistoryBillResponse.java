package com.snackman.datnud11.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryBillResponse {
    private Long billId;
    private int status;
    private Date createTime;
    private Double totalPrice;
    List<BillDetailResponse> billDetail;
}
