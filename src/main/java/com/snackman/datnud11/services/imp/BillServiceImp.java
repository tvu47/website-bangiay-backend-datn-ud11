package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.services.BillService;
import com.snackman.datnud11.services.InventoryService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BillServiceImp implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    private BillDetailsRepository billDetailsRepository;

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
    public void payment(PaymentDTO paymentDTO) {

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
            billDetails.setQuantity(productOrder.getQuantity());
            billDetails.setCost(productOrder.getPrice());
            billDetails.setSaleprice(0d);
            billDetails.setValueDiscount(0d);
            billDetails.setStatus(true);
            billDetails.setCreateTime(new Date());
            this.billDetailsRepository.save(billDetails);
        }

    }
}
