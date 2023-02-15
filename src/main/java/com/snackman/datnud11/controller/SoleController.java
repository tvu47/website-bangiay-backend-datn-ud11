package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Sole;
import com.snackman.datnud11.repo.SoleRepository;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sole")
public class SoleController {
    @Autowired
    SoleRepository soleRepository;
    
    @GetMapping
    public ResponseEntity<List<Sole>> getRolesEmployee(){
        return new ResponseEntity<>(soleRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Sole> createCategory(@RequestBody Sole sole){
        return new ResponseEntity<>(soleRepository.save(sole), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Sole> updateRolesEmployeeById(@RequestBody Sole sole ) throws CustomNotFoundException {
        
        return new ResponseEntity<>(soleRepository.save(sole), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletesoleById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
