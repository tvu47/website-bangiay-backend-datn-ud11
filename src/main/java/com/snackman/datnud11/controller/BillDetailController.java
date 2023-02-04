package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.services.BillDetailService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.generic.GenericObjFindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bill-details")
public class BillDetailController {
    @Autowired
    BillDetailsRepository billDetailsRepository;
    @Autowired
    BillDetailService billDetailService;

    @GetMapping
    public ResponseEntity<List<BillDetails>> getBillDetails(){
        return new ResponseEntity<>(billDetailsRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<BillDetails> createBillDetail(@RequestBody BillDetails billDetails){
        return new ResponseEntity<>(billDetailsRepository.save(billDetails), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<BillDetails> updateBillDetailById(@RequestBody BillDetails billDetails) throws CustomNotFoundException {
        billDetailService.checkBillDetailsExist(billDetails.getBillId());
        return new ResponseEntity<>(billDetailsRepository.save(billDetails), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteBillDetailById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        BillDetails bill = billDetailService.checkBillDetailsExist(id);
        billDetailsRepository.delete(bill);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
