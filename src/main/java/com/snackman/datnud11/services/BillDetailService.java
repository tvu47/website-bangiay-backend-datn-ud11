package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface BillDetailService {
    BillDetails checkBillDetailsExist(Long id) throws CustomNotFoundException;
}
