package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Colors;
import com.snackman.datnud11.repo.ColorsRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/color")
public class ColorsController {
    @Autowired
    ColorsRepository colorsRepository;
    
    @GetMapping
    public ResponseEntity<List<Colors>> getRolesEmployee(){
        return new ResponseEntity<>(colorsRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Colors> createCategory(@RequestBody Colors colors){
        return new ResponseEntity<>(colorsRepository.save(colors), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Colors> updateRolesEmployeeById(@RequestBody Colors colors) throws CustomNotFoundException {
        
        return new ResponseEntity<>(colorsRepository.save(colors), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteColorById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
