package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Size;
import com.snackman.datnud11.repo.SizeRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
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
    SizeRepository sizeRepository;
    
    @GetMapping
    public ResponseEntity<List<Size>> getRolesEmployee(){
        return new ResponseEntity<>(sizeRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Size> createCategory(@RequestBody Size size){
        return new ResponseEntity<>(sizeRepository.save(size), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Size> updateRolesEmployeeById(@RequestBody Size size ) throws CustomNotFoundException {
        
        return new ResponseEntity<>(sizeRepository.save(size), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletesizeById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
