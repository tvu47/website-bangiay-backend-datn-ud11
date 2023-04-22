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
    public List<BillDetailResponse> getBillDetailByBill(Long idBill, int status) {
        return getAllBillDetailOfCustomer(idBill, status);
    }

    @Override
    public List<Bill> getBillByIdCustomer(Long idCustomer, int status) {
        return billRepository.findBillByCustomerId(idCustomer, status);
    }

    private BillDetailResponse swap(BillDetails billDetails, ProductDetail productDetail) {
        BillDetailResponse billDetailResponse = new BillDetailResponse(billDetails);
        billDetailResponse.setProductDetail(productDetail);
        return billDetailResponse;
    }

    private List<BillDetailResponse> getAllBillDetailOfCustomer(Long id, int status) {
            List<BillDetailResponse> billDetailsList = getBillDetailByIdBill(id);
            if (billDetailsList.size() != 0){
                HistoryBillResponse historyBillResponse = new HistoryBillResponse();
                historyBillResponse.setBillDetail(billDetailsList);
            }
        return billDetailsList;
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
