package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

import java.util.List;

public interface BillDetailService {
    BillDetails checkBillDetailsExist(Long id) throws CustomNotFoundException;

    List<BillDetails> findByBillId(Long id);

    BillDetails save(BillDetails billDetails);
}
