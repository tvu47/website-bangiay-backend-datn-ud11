package com.snackman.datnud11.controller;

import com.snackman.datnud11.responses.VoucherResponse;
import com.snackman.datnud11.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
