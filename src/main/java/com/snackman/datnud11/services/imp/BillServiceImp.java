package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.BillStatus;
import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.responses.BillResponse;
import com.snackman.datnud11.services.BillDetailService;
import com.snackman.datnud11.services.BillService;
import com.snackman.datnud11.services.InventoryService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImp implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public Bill checkBillExist(Long id) throws CustomNotFoundException {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return optionalBill.get();
    }

    @Override
    public Bill save(Bill bill) {
        return this.billRepository.save(bill);
    }

    @Override
    public Bill payment(PaymentDTO paymentDTO) {

        Bill bill = new Bill();
        bill.setCustomerId(paymentDTO.getInfo().getCustomerId());
        bill.setCustomerName(paymentDTO.getInfo().getFullName());
        bill.setCreateTime(new Date());
        bill.setStatus(0);
        bill.setAddress(paymentDTO.getInfo().getAddress());
        bill.setEmail(paymentDTO.getInfo().getEmail());
        bill.setPhone(paymentDTO.getInfo().getPhone());
        this.billRepository.save(bill);

        for(PaymentDTO.ProductOrder productOrder : paymentDTO.getProductsOrder()){
            Inventory inventory = this.inventoryService.findBySku(productOrder.getSku());
            inventory.setQuatity(inventory.getQuatity() - productOrder.getQuantity());
            this.inventoryService.save(inventory);

            BillDetails billDetails = new BillDetails();
            billDetails.setBillId(bill.getId());
            billDetails.setProductId(productOrder.getId());
            billDetails.setProductName(productOrder.getProductName());
            billDetails.setColorName(productOrder.getColor().getColorName());
            billDetails.setSizeName(productOrder.getSize().getSizeName());
            billDetails.setQuantity(productOrder.getQuantity());
            billDetails.setCost(productOrder.getPrice());
            billDetails.setColor(productOrder.getColor().getId());
            billDetails.setSize(productOrder.getSize().getId());
            billDetails.setSaleprice(0d);
            billDetails.setValueDiscount(0d);
            billDetails.setStatus(true);
            billDetails.setCreateTime(new Date());
            this.billDetailService.save(billDetails);
        }

        return bill;
    }

    @Override
    public Bill findById(Long id) throws Exception{
        Optional<Bill> optional = this.billRepository.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Không tìm thấy bill " + id);
        }
        return optional.get();
    }

    @Override
    public Bill acceptBill(Long id) throws Exception {
        Bill bill = this.findById(id);
        bill.setStatus(BillStatus.DANG_GIAO.status);
        this.save(bill);
        return bill;
    }

    @Override
    public Bill deliveredBill(Long id) throws Exception {
        Bill bill = this.findById(id);
        bill.setStatus(BillStatus.DA_GIAO.status);
        this.save(bill);
        return bill;
    }

    @Override
    public Bill cancelBill(Long id) throws Exception {
        Bill bill = this.findById(id);
        bill.setStatus(BillStatus.DA_HUY.status);
        this.save(bill);

        List<BillDetails> billDetails = this.billDetailService.findByBillId(id);
        for(BillDetails detail : billDetails){
            String sku = "P" + detail.getProductId() + "C" + detail.getColor() + "S" + detail.getSize();
            Inventory inventory = this.inventoryService.findBySku(sku);
            inventory.setQuatity(inventory.getQuatity() + detail.getQuantity());
            this.inventoryService.save(inventory);
        }

        return bill;
    }


    @Override
    public List<BillResponse> getAllBill() {
        List<BillResponse> bills = new ArrayList<>();
        List<Bill> list = this.billRepository.findAll();

        for(Bill b : list){
            BillResponse billResponse = new BillResponse();
            billResponse.setId(b.getId());
            billResponse.setCustomerName(b.getCustomerName());
            billResponse.setStatus(b.getStatus());
            billResponse.setEmail(b.getEmail());
            billResponse.setPhone(b.getPhone());
            billResponse.setAddress(b.getAddress());
            billResponse.setCreateTime(b.getCreateTimeFormat());
            billResponse.setDetails(this.billDetailService.findByBillId(b.getId()));
            bills.add(billResponse);
        }

        return bills;
    }
}
