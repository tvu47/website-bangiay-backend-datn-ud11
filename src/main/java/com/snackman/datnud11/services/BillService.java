package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.responses.BillResponse;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

import java.util.List;

public interface BillService{
    Bill checkBillExist(Long id) throws CustomNotFoundException;

    Bill save(Bill bill);

    Bill payment(PaymentDTO paymentDTO);

    Bill findById(Long id) throws Exception;

    Bill acceptBill(Long id) throws Exception;

    Bill deliveredBill(Long id) throws Exception;

    Bill cancelBill(Long id) throws Exception;

    List<BillResponse> getAllBill();
}
