package com.snackman.datnud11.controller;

import com.snackman.billservice.entity.Bill;
import com.snackman.billservice.repo.BillRepository;
import com.snackman.billservice.utils.generic.GenericObjFindById;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
@Slf4j
public class BillController {
    @Autowired
    BillRepository billRepository;

    @GetMapping
    public ResponseEntity<List<Bill>> getBills(){
        return new ResponseEntity<>(billRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill){
        return new ResponseEntity<>(billRepository.save(bill), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Bill> updateBillById(@RequestBody Bill billUpdate){
        new GenericObjFindById<Bill>().findByIdObject(billRepository.findById(billUpdate.getId()));
        return new ResponseEntity<>(billRepository.save(billUpdate), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteBillById(@PathVariable(name = "id") Long id){
        Bill bill = new GenericObjFindById<Bill>().findByIdObject(billRepository.findById(id));
        billRepository.delete(bill);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }

}
