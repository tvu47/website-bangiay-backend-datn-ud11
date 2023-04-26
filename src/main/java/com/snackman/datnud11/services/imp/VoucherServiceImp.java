package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.VoucherDTO;
import com.snackman.datnud11.dto.VoucherUpdateDTO;
import com.snackman.datnud11.entity.Voucher;
import com.snackman.datnud11.repo.VoucherRepository;
import com.snackman.datnud11.responses.VoucherResponse;
import com.snackman.datnud11.services.VoucherService;
import com.snackman.datnud11.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImp implements VoucherService {

    @Autowired
    private VoucherRepository repo;

    @Override
    public void deleteById(Long id) {
        try {
            Voucher voucher = this.findById(id);
            voucher.setStatus(false);
            this.save(voucher);
        }catch (Exception e){

        }
    }

    @Override
    public List<VoucherResponse> findAllAvailable(Long customerId) {
        List<Voucher> list =  this.repo.findAllAvailable(customerId);
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        for(Voucher voucher : list){
            if(!voucher.getStatus()){
                continue;
            }
            VoucherResponse response = new VoucherResponse();
            response.setId(voucher.getId());
            response.setCode(voucher.getCode());
            response.setValue(voucher.getValue());
            response.setQuantity(voucher.getQuantity());
            response.setStartTime(TimeUtil.formatTime(voucher.getStartTime(), "yyyy-MM-dd"));
            response.setEndTime(TimeUtil.formatTime(voucher.getEndTime(), "yyyy-MM-dd"));
            response.setStatus(voucher.getStatus());
            response.setOutOfDate(voucher.getEndTime().getTime() > new Date().getTime());
            voucherResponses.add(response);
        }
        return voucherResponses;
    }

    @Override
    public List<VoucherResponse> findAllVouchers() {
        List<Voucher> list =  this.repo.findAll();
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        for(Voucher voucher : list){
            if(!voucher.getStatus()){
                continue;
            }
            VoucherResponse response = new VoucherResponse();
            response.setId(voucher.getId());
            response.setCode(voucher.getCode());
            response.setValue(voucher.getValue());
            response.setQuantity(voucher.getQuantity());
            response.setStartTime(TimeUtil.formatTime(voucher.getStartTime(), "yyyy-MM-dd"));
            response.setEndTime(TimeUtil.formatTime(voucher.getEndTime(), "yyyy-MM-dd"));
            response.setStatus(voucher.getStatus());
            response.setOutOfDate(voucher.getEndTime().getTime() < new Date().getTime());
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

    @Override
    public VoucherResponse save(VoucherDTO voucherDTO) throws Exception{
        Voucher exist = this.repo.findByCode(voucherDTO.getCode());
        if(exist != null){
            throw new Exception("Code existed");
        }

        if(voucherDTO.getValue() <= 0 || voucherDTO.getValue() > 100){
            throw new Exception("Invalid value");
        }
        if(voucherDTO.getQuantity() <= 0){
            throw new Exception("Invalid quantity");
        }
        if(voucherDTO.getStartTime().getTime() > voucherDTO.getEndTime().getTime()){
            throw new Exception("Invalid expired");
        }
        Voucher voucher = new Voucher();
        voucher.setCode(voucherDTO.getCode());
        voucher.setValue(voucherDTO.getValue());
        voucher.setQuantity(voucherDTO.getQuantity());
        voucher.setStartTime(voucherDTO.getStartTime());
        voucher.setEndTime(voucherDTO.getEndTime());
        voucher.setStatus(true);
        this.repo.save(voucher);


        VoucherResponse response = new VoucherResponse();
        response.setId(voucher.getId());
        response.setCode(voucher.getCode());
        response.setValue(voucher.getValue());
        response.setQuantity(voucher.getQuantity());
        response.setStartTime(TimeUtil.formatTime(voucher.getStartTime(), "yyyy-MM-dd"));
        response.setEndTime(TimeUtil.formatTime(voucher.getEndTime(), "yyyy-MM-dd"));
        response.setStatus(voucher.getStatus());
        response.setOutOfDate(voucher.getEndTime().getTime() > new Date().getTime());

        return response;
    }

    @Override
    public VoucherResponse update(VoucherUpdateDTO voucherUpdateDTO) throws Exception {
        Voucher voucher = this.findById(voucherUpdateDTO.getId());
        voucher.setCode(voucherUpdateDTO.getCode());
        voucher.setValue(voucherUpdateDTO.getValue());
        voucher.setQuantity(voucherUpdateDTO.getQuantity());
        voucher.setStartTime(voucherUpdateDTO.getStartTime());
        voucher.setEndTime(voucherUpdateDTO.getEndTime());
        this.save(voucher);

        VoucherResponse response = new VoucherResponse();
        response.setId(voucher.getId());
        response.setCode(voucher.getCode());
        response.setValue(voucher.getValue());
        response.setQuantity(voucher.getQuantity());
        response.setStartTime(TimeUtil.formatTime(voucher.getStartTime(), "yyyy-MM-dd"));
        response.setEndTime(TimeUtil.formatTime(voucher.getEndTime(), "yyyy-MM-dd"));
        response.setStatus(voucher.getStatus());
        response.setOutOfDate(voucher.getEndTime().getTime() > new Date().getTime());
        return response;
    }
}
