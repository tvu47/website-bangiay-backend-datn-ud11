package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.Count;
import com.snackman.datnud11.entity.Images;
import com.snackman.datnud11.exceptions.CustomMessageException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.repo.ImagesRepository;
import com.snackman.datnud11.responses.BillDetailResponse;
import com.snackman.datnud11.responses.HistoryResponse;
import com.snackman.datnud11.services.*;
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
    private final ImagesRepository imagesRepository;
    private final BillRepository billRepository;

    @Override
    public Map<Long, List<BillDetailResponse>> getHistoryPerchaseOfCustomer(String username) throws UserNotfoundException, CustomMessageException {
        return getAllBillDetailOfCustomer(username);
    }

    private BillDetailResponse swap(BillDetails billDetails, Images ima) {
        BillDetailResponse billDetailResponse = new BillDetailResponse(billDetails);
        billDetailResponse.setImgProducts(ima);
        return billDetailResponse;
    }

    private Map<Long, List<BillDetailResponse>> getAllBillDetailOfCustomer(String username) throws UserNotfoundException, CustomMessageException {
        Long idCustomer = getIdCustomerByCustomerName(username);
        Map<Long, List<BillDetailResponse>> BillDetailResponseMAp = new HashMap<>();

        List<Long> ids = getListIdBillByCustomerId(idCustomer);

        ids.stream().forEach(id -> {
            List<BillDetailResponse> billDetailsList = getBillDetailByIdBill(id);
            BillDetailResponseMAp.put(id, billDetailsList);
        });
        return BillDetailResponseMAp;
    }

    public Long getIdCustomerByCustomerName(String username) throws UserNotfoundException {
        return customerService.findCustomerByEmail(username).getId();
    }

    public List<Long> getListIdBillByCustomerId(Long idCustomer) throws CustomMessageException {
        List<Bill> bill = billRepository.findBillByCustomerId(idCustomer);
        if (bill.size() == 0) {
            throw new CustomMessageException("Chưa mua hàng nên chưa có lịch sử");
        }
        List<Long> longList = new ArrayList<>();
        bill.stream().forEach(b -> longList.add(b.getId()));
        return longList;
    }

    private List<BillDetailResponse> getBillDetailByIdBill(Long longList) {
        List<BillDetailResponse> billDetailResponseList = new ArrayList<>();
        Map<Long, Images> imagesMap = getMapImageWithBillDetailIdKeyByIdBill(longList);
        billDetailsRepository.getBillDetailsByIdBill(longList).stream().forEach(billDetails -> {
            BillDetailResponse billDetailResponse = this.swap(billDetails, imagesMap.get(billDetails.getBillDetailId()));
            billDetailResponseList.add(billDetailResponse);
        });
        return billDetailResponseList;
    }

    private Map<Long, Images> getMapImageWithBillDetailIdKeyByIdBill(Long longList) {
        // get id list product in database
        List<Count> listIdProduct = billDetailsRepository.getIdProduct(longList);

        Map<Long, Images> imageWithBillDetailId = new HashMap<>();
        // array
        listIdProduct.stream().forEach(ls -> {
            Images ima = imagesRepository.findByProductIdTop1(ls.getProductId());
            imageWithBillDetailId.put(ls.getBillDetailId(), ima);
        });
        return imageWithBillDetailId;
    }

}
