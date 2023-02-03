package com.snackman.datnud11.controller;

import com.snackman.billservice.entity.Customers;
import com.snackman.billservice.repo.CustomersRepository;
import com.snackman.billservice.utils.generic.GenericObjFindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    CustomersRepository customersRepository;

    @GetMapping
    public ResponseEntity<List<Customers>> getCustomers(){
        return new ResponseEntity<>(customersRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Customers> createCustomers(@RequestBody Customers customers){
        return new ResponseEntity<>(customersRepository.save(customers), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Customers> updateCustomerById(@RequestBody Customers customers){
        Customers customersUpdate = new GenericObjFindById<Customers>().findByIdObject(customersRepository.findById(customers.getId()));
        return new ResponseEntity<>(customersRepository.save(customersUpdate), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteCustomersById(@PathVariable(name = "id") Long id){
        Customers customers = new GenericObjFindById<Customers>().findByIdObject(customersRepository.findById(id));
        customersRepository.delete(customers);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
