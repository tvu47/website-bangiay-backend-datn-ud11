package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.VoucherDTO;
import com.snackman.datnud11.dto.VoucherUpdateDTO;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.entity.Voucher;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.responses.NoticeResponse;
import com.snackman.datnud11.responses.VoucherResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.VoucherService;
import com.snackman.datnud11.services.auth.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherController {

    @Autowired
    private VoucherService service;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/available")
    public ResponseEntity<List<VoucherResponse>> getVoucherAvailable() throws UserNotfoundException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customers customers = customerService.findCustomerByEmail(userAuth.getUsername());
        return new ResponseEntity<>(this.service.findAllAvailable(customers.getId()), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<VoucherResponse> updateVoucher(@RequestBody VoucherUpdateDTO voucherDTO) throws Exception {
        return new ResponseEntity<>(this.service.update(voucherDTO), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VoucherResponse>> getAllVouchers(){
        return new ResponseEntity<>(this.service.findAllVouchers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<NoticeResponse> addNewVoucher(@RequestBody VoucherDTO voucherDTO) throws Exception {
        try {
            VoucherResponse voucher = this.service.save(voucherDTO);
            return new ResponseEntity<>(new NoticeResponse(HttpStatus.OK.value(), "Thêm mới thành công!", voucher), HttpStatus.OK);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<NoticeResponse> deleteVoucher(@PathVariable(name = "id") Long id) throws Exception{
        this.service.deleteById(id);
        return ResponseEntity.ok(new NoticeResponse(200,"Xóa thành công!", ""));
    }

}
