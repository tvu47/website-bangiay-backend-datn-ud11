package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Discounts;
import com.snackman.datnud11.repo.DiscountsRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountsController {
    @Autowired
    DiscountsRepository discountsRepository;
    
    @GetMapping
    public ResponseEntity<List<Discounts>> getRolesEmployee(){
        return new ResponseEntity<>(discountsRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Discounts> createCategory(@RequestBody Discounts discounts){
        return new ResponseEntity<>(discountsRepository.save(discounts), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Discounts> updateRolesEmployeeById(@RequestBody Discounts discounts) throws CustomNotFoundException {
        
        return new ResponseEntity<>(discountsRepository.save(discounts), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletediscountsById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
