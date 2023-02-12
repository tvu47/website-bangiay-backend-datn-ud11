package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Category;
import com.snackman.datnud11.repo.CategoryRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorys")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    
    @GetMapping
    public ResponseEntity<List<Category>> getRolesEmployee(){
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Category> updateRolesEmployeeById(@RequestBody Category category) throws CustomNotFoundException {
        
        return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCatrgoryById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
