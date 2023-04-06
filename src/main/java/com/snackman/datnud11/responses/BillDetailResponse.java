package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.Images;
import jakarta.persistence.Column;
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
public class BillDetailResponse {
    private List<Images> imgProducts;
    private String colorName;
    private String sizeName;
    private Long billId;
    private Integer quantity;
    private Double cost;
    private Double saleprice;
    private Double valueDiscount;
    private Date createTime;
    private Boolean status;

    public BillDetailResponse(BillDetails billDetails){
        this.colorName = billDetails.getColorName();
        this.sizeName = billDetails.getSizeName();
        this.billId = billDetails.getBillId();
        this.quantity = billDetails.getQuantity();
        this.cost = billDetails.getCost();
        this.saleprice = billDetails.getSaleprice();
        this.valueDiscount = billDetails.getValueDiscount();
        this.createTime = billDetails.getCreateTime();
        this.status = billDetails.getStatus();
    }
}
