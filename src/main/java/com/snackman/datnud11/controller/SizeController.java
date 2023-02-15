package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.SizeDTO;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.entity.Size;
import com.snackman.datnud11.services.ProductService;
import com.snackman.datnud11.services.SizeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;
    
    @GetMapping()
    public ResponseEntity<List<Size>> getAll(){
        return new ResponseEntity<>(this.sizeService.findAll(), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Size> create(@RequestBody SizeDTO sizeDTO) throws Exception {
        return new ResponseEntity<>(this.sizeService.save(new Size(sizeDTO)), HttpStatus.CREATED);
    }
    @PutMapping()
    public ResponseEntity<Size> updateRolesEmployeeById(@RequestBody SizeDTO sizeDTO ) throws Exception {
        return new ResponseEntity<>(this.sizeService.save(new Size(sizeDTO)), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletesizeById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        this.sizeService.delete(id);
        return ResponseEntity.ok("Delete Successfully!");
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Size>> findByProductId(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(this.sizeService.findByProductId(id), HttpStatus.OK);
    }
}
