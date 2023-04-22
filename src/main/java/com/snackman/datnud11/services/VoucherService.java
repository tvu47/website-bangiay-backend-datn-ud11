package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.VoucherDTO;
import com.snackman.datnud11.dto.VoucherUpdateDTO;
import com.snackman.datnud11.entity.Voucher;
import com.snackman.datnud11.responses.VoucherResponse;

import java.util.List;

public interface VoucherService {

    void deleteById(Long id);

    List<VoucherResponse> findAllAvailable(Long customerId);

    List<VoucherResponse> findAllVouchers();

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Voucher findByCode(String code);

    Voucher findById(Long id) throws Exception;

    VoucherResponse save(VoucherDTO voucherDTO) throws Exception;

    VoucherResponse update(VoucherUpdateDTO voucherUpdateDTO) throws Exception;

}
