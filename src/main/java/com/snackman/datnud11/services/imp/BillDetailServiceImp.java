package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.services.BillDetailService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillDetailServiceImp implements BillDetailService {
    @Autowired
    BillDetailsRepository billDetailsRepository;
    @Override
    public BillDetails checkBillDetailsExist(Long id) throws CustomNotFoundException {
        Optional<BillDetails> billDetailsOptional = billDetailsRepository.findById(id);
        if (billDetailsOptional.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return billDetailsOptional.get();
    }
}
