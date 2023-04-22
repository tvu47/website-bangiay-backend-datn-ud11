package com.snackman.datnud11.services;

import com.snackman.datnud11.exceptions.CustomMessageException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.responses.BillDetailResponse;
import com.snackman.datnud11.responses.HistoryBillResponse;
import com.snackman.datnud11.responses.HistoryResponse;

import java.util.List;
import java.util.Map;

public interface HistoryService {
    List<HistoryBillResponse> getHistoryPerchaseOfCustomer(String username, int status) throws UserNotfoundException, CustomMessageException;
}
