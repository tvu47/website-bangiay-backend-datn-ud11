package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.BillDetails;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillDetailThongKeResponse extends BillDetails {
    private Long totalQuantity;

    public BillDetailThongKeResponse(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
