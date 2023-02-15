package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.repo.InventoryRepository;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;
    
    @GetMapping
    public ResponseEntity<List<Inventory>> getRolesEmployee(){
        return new ResponseEntity<>(inventoryRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Inventory> createCategory(@RequestBody Inventory inventory){
        return new ResponseEntity<>(inventoryRepository.save(inventory), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Inventory> updateRolesEmployeeById(@RequestBody Inventory inventory) throws CustomNotFoundException {
        
        return new ResponseEntity<>(inventoryRepository.save(inventory), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteinventoryById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
