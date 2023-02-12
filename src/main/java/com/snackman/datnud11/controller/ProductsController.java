package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.repo.ProductsRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    @Autowired
    ProductsRepository productsRepository;
    
    @GetMapping
    public ResponseEntity<List<Products>> getRolesEmployee(){
        return new ResponseEntity<>(productsRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Products> createCategory(@RequestBody Products products){
        return new ResponseEntity<>(productsRepository.save(products), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Products> updateRolesEmployeeById(@RequestBody Products product ) throws CustomNotFoundException {
        
        return new ResponseEntity<>(productsRepository.save(product), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteproductById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
