package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface BillService{
    Bill checkBillExist(Long id) throws CustomNotFoundException;

    void payment(PaymentDTO paymentDTO);
}
