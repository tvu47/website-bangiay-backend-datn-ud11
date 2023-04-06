package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.responses.BillDetailResponse;
import com.snackman.datnud11.services.BillDetailService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillDetailServiceImp implements BillDetailService {
    @Autowired
    BillDetailsRepository repo;
    @Override
    public BillDetails checkBillDetailsExist(Long id) throws CustomNotFoundException {
        Optional<BillDetails> billDetailsOptional = repo.findById(id);
        if (billDetailsOptional.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return billDetailsOptional.get();
    }

    @Override
    public List<BillDetails> findByBillId(Long id) {
        return this.repo.findByBillId(id);
    }

    @Override
    public BillDetails save(BillDetails billDetails) {
        return this.repo.save(billDetails);
    }

}
