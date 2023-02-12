package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Images;
import com.snackman.datnud11.repo.ImagesRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {
    @Autowired
    ImagesRepository imagesRepository;
    
    @GetMapping
    public ResponseEntity<List<Images>> getRolesEmployee(){
        return new ResponseEntity<>(imagesRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Images> createCategory(@RequestBody Images images){
        return new ResponseEntity<>(imagesRepository.save(images), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Images> updateRolesEmployeeById(@RequestBody Images images) throws CustomNotFoundException {
        
        return new ResponseEntity<>(imagesRepository.save(images), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteimageById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
