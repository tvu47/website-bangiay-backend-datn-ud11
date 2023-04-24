package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.dto.request.DeleteProductInBillRequest;
import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.responses.BillResponse;
import com.snackman.datnud11.responses.BillResponseHistory;
import com.snackman.datnud11.responses.Count;
import com.snackman.datnud11.responses.ProductDetailThongKeResponse;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.apache.xmlbeans.impl.store.Locale;

import java.util.Date;
import java.util.List;

public interface BillService{
    Bill checkBillExist(Long id) throws CustomNotFoundException;

    Bill save(Bill bill);

    Bill payment(PaymentDTO paymentDTO) throws Exception;

    Bill findById(Long id) throws Exception;

    Bill acceptBill(Long id) throws Exception;

    Bill deliveredBill(Long id) throws Exception;

    Bill cancelBill(Long id) throws Exception;

    Bill deliverBill(Long id) throws Exception;

    List<BillResponse> getAllBill() throws Exception;

    List<BillResponse> findByNameOrPhone(String find) throws Exception;
    List<BillResponse> getAllBillByStatus(Integer status) throws Exception;

    BillResponse deleteProductInBill(DeleteProductInBillRequest request) throws Exception;

    Count findBillFromBeginDateToEndDate(Date start, Date end);
    Count findBillFromBeginDateToEndDateHuy(Date start, Date end);
    Count findAllBillAmount();
    Count findAllBillAmountHuy();

    ProductDetailThongKeResponse getProductThongKe();
    ProductDetailThongKeResponse getProductThongKeTheoKhoangNgay(Date start, Date end);
}
