package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.InventoryDTO;
import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.repo.InventoryRepository;
import com.snackman.datnud11.responses.InventoryResponse;
import com.snackman.datnud11.responses.NoticeResponse;
import com.snackman.datnud11.services.InventoryService;
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
    private InventoryService inventoryService;
    
    @GetMapping
    public ResponseEntity<List<Inventory>> getRolesEmployee(){
//        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
        return null;
    }
    @PostMapping
    public ResponseEntity<Inventory> createCategory(@RequestBody Inventory inventory){
//        return new ResponseEntity<>(inventoryRepository.save(inventory), HttpStatus.CREATED);
        return null;
    }
    @PutMapping
    public ResponseEntity<Inventory> updateRolesEmployeeById(@RequestBody Inventory inventory) throws CustomNotFoundException {
        
//        return new ResponseEntity<>(inventoryRepository.save(inventory), HttpStatus.CREATED);
        return null;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteinventoryById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> findByProductId(@PathVariable(name = "id") Long id) throws Exception {
        return new ResponseEntity<>(this.inventoryService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/store")
    public ResponseEntity<NoticeResponse> storeInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception{
        Inventory inventory = this.inventoryService.save(inventoryDTO);
        return new ResponseEntity<>(new NoticeResponse(HttpStatus.OK.value(), "Thêm thành công!", inventory), HttpStatus.OK);
    }
}
