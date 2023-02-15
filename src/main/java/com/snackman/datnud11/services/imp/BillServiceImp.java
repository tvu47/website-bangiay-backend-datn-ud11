package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.services.BillService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillServiceImp implements BillService {
    @Autowired
    BillRepository billRepository;
    @Override
    public Bill checkBillExist(Long id) throws CustomNotFoundException {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return optionalBill.get();
    }
}
