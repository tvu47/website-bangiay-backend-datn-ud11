package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Voucher;
import com.snackman.datnud11.repo.VoucherRepository;
import com.snackman.datnud11.responses.VoucherResponse;
import com.snackman.datnud11.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImp implements VoucherService {

    @Autowired
    private VoucherRepository repo;

    @Override
    public List<VoucherResponse> findAllAvailable() {
        List<Voucher> list =  this.repo.findAllAvailable();
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        for(Voucher voucher : list){
            VoucherResponse response = new VoucherResponse();
            response.setId(voucher.getId());
            response.setCode(voucher.getCode());
            response.setValue(voucher.getValue());
            response.setQuantity(voucher.getQuantity());
            response.setStartTime(voucher.getStartTime());
            response.setEndTime(voucher.getEndTime());
            response.setStatus(voucher.getStatus());
            voucherResponses.add(response);
        }
        return voucherResponses;
    }

    @Override
    public Voucher save(Voucher voucher) {
        return this.repo.save(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return this.repo.findAll();
    }

    @Override
    public Voucher findByCode(String code) {
        return this.repo.findByCode(code);
    }

    @Override
    public Voucher findById(Long id) throws Exception {
        Optional<Voucher> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Không tìm thấy voucher: " + id);
        }
        return optional.get();
    }
}
