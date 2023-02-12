package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Materials;
import com.snackman.datnud11.repo.MaterialsRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materials")
public class MaterialsController {
    @Autowired
    MaterialsRepository materialsRepository;
    
    @GetMapping
    public ResponseEntity<List<Materials>> getRolesEmployee(){
        return new ResponseEntity<>(materialsRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Materials> createCategory(@RequestBody Materials materials){
        return new ResponseEntity<>(materialsRepository.save(materials), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Materials> updateRolesEmployeeById(@RequestBody Materials materials) throws CustomNotFoundException {
        
        return new ResponseEntity<>(materialsRepository.save(materials), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletematerialsById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
