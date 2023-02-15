package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.ProductColor;
import com.snackman.datnud11.repo.ProductColorRepository;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productColor")
public class ProductColorController {
    @Autowired
    ProductColorRepository productColorRepository;
    
    @GetMapping
    public ResponseEntity<List<ProductColor>> getRolesEmployee(){
        return new ResponseEntity<>(productColorRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductColor> createCategory(@RequestBody ProductColor productColor){
        return new ResponseEntity<>(productColorRepository.save(productColor), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<ProductColor> updateRolesEmployeeById(@RequestBody ProductColor productColor ) throws CustomNotFoundException {
        
        return new ResponseEntity<>(productColorRepository.save(productColor), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductColorById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
