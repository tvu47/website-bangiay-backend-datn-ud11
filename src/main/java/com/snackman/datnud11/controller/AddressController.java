package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Address;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.repo.AddressRepository;
import com.snackman.datnud11.responses.BillResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.auth.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController {
    private final AddressRepository repository;
    @Autowired
    private CustomerService customerService;
    @GetMapping
    public ResponseEntity<List<Address>> getAddressByCustomer() {
        UserAuth user = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customers customers = customerService.findCustomerByEmail(user.getUsername());
        return new ResponseEntity<>(repository.findAddressByCustomerId(customers.getId()).get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Address> createAddressWithIdCustomer(@RequestParam(value = "address", required = false) String address) {
        UserAuth user = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customers customers = customerService.findCustomerByEmail(user.getUsername());
        Address address1 = new Address();
        address1.setAddress(address);
        address1.setCustomerId(customers.getId());

        return new ResponseEntity<>(repository.save(address1), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        return new ResponseEntity<>(repository.save(address), HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteAddress(@RequestParam(value = "id") Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("address deleted.", HttpStatus.NO_CONTENT);
    }
}
