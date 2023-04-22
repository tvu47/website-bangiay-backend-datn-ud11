package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.Bill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongKe extends BillResponseHistory{
    private Double amount;

    public ThongKe(Bill bill, Double amount) {
        super(bill);
        this.amount = amount;
    }
}
