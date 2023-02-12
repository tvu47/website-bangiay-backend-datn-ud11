package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.PaymentType;
import com.snackman.datnud11.repo.PaymentTypeRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paymentType")
public class PaymentTypeController {
    @Autowired
    PaymentTypeRepository paymentTypeRepository;
    
    @GetMapping
    public ResponseEntity<List<PaymentType>> getRolesEmployee(){
        return new ResponseEntity<>(paymentTypeRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PaymentType> createCategory(@RequestBody PaymentType paymentType){
        return new ResponseEntity<>(paymentTypeRepository.save(paymentType), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<PaymentType> updateRolesEmployeeById(@RequestBody PaymentType paymentType) throws CustomNotFoundException {
        
        return new ResponseEntity<>(paymentTypeRepository.save(paymentType), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletepaymentTypeById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
