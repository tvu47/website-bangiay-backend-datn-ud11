package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.BillDetails;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BillDetailThongKeResponse extends BillDetails {
    private Long totalQuantity;

    public BillDetailThongKeResponse(Long totalQuantity, BillDetails billDetails) {
        this.totalQuantity = totalQuantity;
        this.setBillId(billDetails.getBillId());
        this.setBillDetailId(billDetails.getBillDetailId());
        this.setCost(billDetails.getCost());
        this.setColor(billDetails.getColor());
        this.setColorName(billDetails.getColorName());
        this.setProductId(billDetails.getProductId());
        this.setSaleprice(billDetails.getSaleprice());
        this.setProductName(billDetails.getProductName());
        this.setCreateTime(billDetails.getCreateTime());
        this.setQuantity(billDetails.getQuantity());
        this.setSize(billDetails.getSize());
        this.setSizeName(billDetails.getSizeName());
        this.setStatus(billDetails.getStatus());
    }
}
