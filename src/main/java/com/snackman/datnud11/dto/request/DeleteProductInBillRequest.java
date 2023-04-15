package com.snackman.datnud11.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductInBillRequest {

    private Long billId;
    private Long productId;
    private Long colorId;
    private Long sizeId;

}
