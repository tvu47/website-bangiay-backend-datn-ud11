package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.BillDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRes {
    private String productName;
    private String colorName;
    private String sizeName;
//    private Long totalQuantity;
    private int quantity;
    private double cost;
    private double salePrice;
    private Date createTime;

    public ProductRes(BillDetails billDetails) {
        this.productName = billDetails.getProductName();
        this.colorName = billDetails.getColorName();
        this.sizeName = billDetails.getSizeName();
        this.salePrice = billDetails.getSaleprice();
        this.createTime = billDetails.getCreateTime();
        this.quantity = billDetails.getQuantity();
        this.cost = billDetails.getCost();
    }
}
