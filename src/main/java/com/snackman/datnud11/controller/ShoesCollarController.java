package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.ShoesCollar;
import com.snackman.datnud11.repo.ShoesCollarRepository;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shoesCollar")
public class ShoesCollarController {
    @Autowired
    ShoesCollarRepository shoesCollarRepository;
    
    @GetMapping
    public ResponseEntity<List<ShoesCollar>> getRolesEmployee(){
        return new ResponseEntity<>(shoesCollarRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ShoesCollar> createCategory(@RequestBody ShoesCollar shoesCollar){
        return new ResponseEntity<>(shoesCollarRepository.save(shoesCollar), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<ShoesCollar> updateRolesEmployeeById(@RequestBody ShoesCollar shoesCollar ) throws CustomNotFoundException {
        
        return new ResponseEntity<>(shoesCollarRepository.save(shoesCollar), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteshoesCollarById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
