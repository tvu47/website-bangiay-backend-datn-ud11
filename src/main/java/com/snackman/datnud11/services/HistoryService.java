package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.exceptions.CustomMessageException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.responses.BillDetailResponse;
import com.snackman.datnud11.responses.HistoryBillResponse;
import com.snackman.datnud11.responses.HistoryResponse;

import java.util.List;
import java.util.Map;

public interface HistoryService {
    List<BillDetailResponse> getBillDetailByBill(Long idBill);
    List<Bill> getBillByIdCustomer(Long idCustomer, int status);

}
