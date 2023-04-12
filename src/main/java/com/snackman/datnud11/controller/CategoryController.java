package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.CategoryDTO;
import com.snackman.datnud11.entity.Category;
import com.snackman.datnud11.services.CategoryService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(this.categoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable(name = "id") Long id) throws Exception{
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(this.categoryService.save(new Category(categoryDTO)), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDTO categoryDTO) throws CustomNotFoundException {
        return new ResponseEntity<>(this.categoryService.save(new Category(categoryDTO)), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCatrgoryById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        this.categoryService.delete(id);
        return ResponseEntity.ok("Delete successfully!");
    }
}
