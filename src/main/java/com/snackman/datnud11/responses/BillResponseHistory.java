package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.Voucher;
import com.snackman.datnud11.utils.TimeUtil;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponseHistory {
    private Long id;

    private String customerName;

    private String createTime;

    private Integer status;

    private String address;

    private String email;

    private String phone;

    private Voucher voucher;

    private Integer discount;

    private Double totalPrice;

    public BillResponseHistory(Bill bill) {
        this.id = bill.getId();
        this.customerName = bill.getCustomerName();
        this.address = bill.getAddress();
        this.email = bill.getAddress();
        this.phone = bill.getPhone();
        this.createTime = TimeUtil.formatTime(bill.getCreateTime(), "yyyy-MM-dd");
        this.status = bill.getStatus();
        this.discount = bill.getDiscount();
        this.totalPrice = bill.getTotalPrice();
    }
}
