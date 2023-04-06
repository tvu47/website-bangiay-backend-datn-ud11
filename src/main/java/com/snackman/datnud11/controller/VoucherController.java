package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.VoucherDTO;
import com.snackman.datnud11.entity.Voucher;
import com.snackman.datnud11.responses.NoticeResponse;
import com.snackman.datnud11.responses.VoucherResponse;
import com.snackman.datnud11.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherController {

    @Autowired
    private VoucherService service;

    @GetMapping("/available")
    public ResponseEntity<List<VoucherResponse>> getVoucherAvailable(){
        return new ResponseEntity<>(this.service.findAllAvailable(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VoucherResponse>> getAllVouchers(){
        return new ResponseEntity<>(this.service.findAllVouchers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<NoticeResponse> addNewVoucher(@RequestBody VoucherDTO voucherDTO) throws Exception {
        VoucherResponse voucher = this.service.save(voucherDTO);
        return new ResponseEntity<>(new NoticeResponse(HttpStatus.OK.value(), "Thêm mới thành công!", voucher), HttpStatus.OK);
    }

}
