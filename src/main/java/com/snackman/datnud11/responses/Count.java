package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.Voucher;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
public class Count {
    private Long count;
    private Double amount;

    private List<BillResponseHistory> billResponseHistories;

    public Count(Long count, Double amount) {
        this.count = count;
        this.amount = amount;
    }

}
