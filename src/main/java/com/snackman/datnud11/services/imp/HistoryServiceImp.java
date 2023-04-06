package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.Images;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.repo.ImagesRepository;
import com.snackman.datnud11.responses.BillDetailResponse;
import com.snackman.datnud11.responses.HistoryResponse;
import com.snackman.datnud11.services.*;
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
@Slf4j
public class HistoryServiceImp implements HistoryService {

    private final CustomerService customerService;
    private final BillDetailsRepository billDetailsRepository;
    private final ImagesRepository imagesRepository;
    private final ProductService productService;

    private final BillRepository billRepository;
    private static Map<Long, List<Images>> listImage;

    @Override
    public Map<Long, List<BillDetailResponse>> getHistoryPerchaseOfCustomer(String username) throws UserNotfoundException {
        Map<Long, List<BillDetailResponse>> historyResponseList = new HashMap<>();
        getAllBillDetailOfCustomer(username).forEach((k,v) ->{
            historyResponseList.put(k, swap(v));
        });
        return historyResponseList;
    }

    public List<BillDetailResponse> swap(List<BillDetails> billDetails){
        List<BillDetailResponse> longListMap = new ArrayList<>();
        billDetails.stream().forEach(billDetails1 -> {
            BillDetailResponse billDetailResponse = new BillDetailResponse(billDetails1);
            billDetailResponse.setImgProducts(listImage.get(billDetails1.getProductId()));
            longListMap.add(billDetailResponse);
        });
        return longListMap;
    }

    public Map<Long,List<BillDetails>> getAllBillDetailOfCustomer(String username) throws UserNotfoundException {
        Long idCustomer = getIdCustomerByCustomerName(username);
        Map<Long,List<BillDetails>> longListMap = new HashMap<>();

        List<Long> ids = getListIdBillByCustomerId(idCustomer);

        ids.stream().forEach(id ->{
            List<BillDetails> billDetailsList = getBillDetailByIdBill(id);
            longListMap.put(id, billDetailsList);
        });
        return longListMap;
    }

    public Long getIdCustomerByCustomerName(String username) throws UserNotfoundException {
        return customerService.findCustomerByEmail(username).getId();
    }

    public List<Long> getListIdBillByCustomerId(Long idCustomer){
        List<Bill> bill = billRepository.findBillByCustomerId(idCustomer);
        if (bill.size()==0){
            throw new NullPointerException("Chưa mua hàng nên chưa có lịch sử");
        }
        List<Long> longList = new ArrayList<>();
        bill.stream().forEach(b -> longList.add(b.getId()));
        return longList;
    }

    private List<BillDetails> getBillDetailByIdBill(Long longList){
        List<BillDetails>billDetailsList = billDetailsRepository.getBillDetailsByListIdBill(longList);
        this.getListImageByListIdProduct(billDetailsList);
        return billDetailsList;
    }

    private void getListImageByListIdProduct(List<BillDetails>billDetailsList){
        Map<Long, List<Images>> listImageWithId = new HashMap<>();
        List<Long> idProduct = new ArrayList<>();
        if (billDetailsList.isEmpty()){
            throw new NullPointerException("billDetailsList is empty");
        }
        billDetailsList.stream().forEach(billDetails -> idProduct.add(billDetails.getProductId()));
        idProduct.stream().forEach(id -> {
            List<Images> imagesList = imagesRepository.findByProductId(id);
            listImageWithId.put(id, imagesList);
        });
    }

}
