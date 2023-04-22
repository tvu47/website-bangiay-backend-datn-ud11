package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.*;
import com.snackman.datnud11.exceptions.CustomMessageException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.repo.ImagesRepository;
import com.snackman.datnud11.repo.ProductDetailRepository;
import com.snackman.datnud11.responses.BillDetailResponse;
import com.snackman.datnud11.responses.HistoryBillResponse;
import com.snackman.datnud11.responses.HistoryResponse;
import com.snackman.datnud11.services.*;
import com.snackman.datnud11.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HistoryServiceImp implements HistoryService {

    private final CustomerService customerService;
    private final BillDetailsRepository billDetailsRepository;
    private final ProductDetailRepository productDetailRepository;
    private final BillRepository billRepository;

    @Override
    public List<HistoryBillResponse> getHistoryPerchaseOfCustomer(String username, int status) throws UserNotfoundException, CustomMessageException {
        return getAllBillDetailOfCustomer(username, status);
    }

    private BillDetailResponse swap(BillDetails billDetails, ProductDetail productDetail) {
        BillDetailResponse billDetailResponse = new BillDetailResponse(billDetails);
        billDetailResponse.setProductDetail(productDetail);
        return billDetailResponse;
    }

    private List<HistoryBillResponse> getAllBillDetailOfCustomer(String username, int status) throws UserNotfoundException, CustomMessageException {
        Long idCustomer = getIdCustomerByCustomerName(username);
        List<HistoryBillResponse> historyBillResponses = new ArrayList<>();

        List<Bill> ids = getListIdBillByCustomerId(idCustomer, status);

        ids.stream().forEach(bill -> {
            List<BillDetailResponse> billDetailsList = getBillDetailByIdBill(bill.getId());
            if (billDetailsList.size() != 0){
                HistoryBillResponse historyBillResponse = new HistoryBillResponse();
                historyBillResponse.setBillId(bill.getId());
                historyBillResponse.setStatus(bill.getStatus());
                historyBillResponse.setBillDetail(billDetailsList);
                historyBillResponse.setCreateTime(bill.getCreateTime());
                historyBillResponse.setTotalPrice(bill.getTotalPrice());
                historyBillResponses.add(historyBillResponse);
            }
        });
        return historyBillResponses;
    }

    public Long getIdCustomerByCustomerName(String username) throws UserNotfoundException {
        return customerService.findCustomerByEmail(username).getId();
    }

    public List<Bill> getListIdBillByCustomerId(Long idCustomer, int status) throws CustomMessageException {
        List<Bill> bill;
        if (status==-7){
            bill= billRepository.findAllBillByCustomerId(idCustomer);
        }else{
            bill= billRepository.findBillByCustomerId(idCustomer, status);
        }
        if (bill.size() == 0) {
            throw new CustomMessageException("Chưa mua hàng nên chưa có lịch sử");
        }
        return bill;
    }

    private List<BillDetailResponse> getBillDetailByIdBill(Long id) {
        List<BillDetailResponse> billDetailResponseList = new ArrayList<>();
        billDetailsRepository.getBillDetailsByIdBill(id).stream().forEach(billDetails -> {
            BillDetailResponse billDetailResponse = this.swap(billDetails,
                    this.productDetailRepository.findProductDetailByProductIdTop1(billDetails.getProductId(),billDetails.getColor()));
            billDetailResponseList.add(billDetailResponse);
        });
        return billDetailResponseList;
    }



}
