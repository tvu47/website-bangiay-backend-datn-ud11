package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.ImageDTO;
import com.snackman.datnud11.entity.Images;
import com.snackman.datnud11.repo.ImagesRepository;
import com.snackman.datnud11.services.ImageService;
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
    private ImageService imageService;
    
    @GetMapping
    public ResponseEntity<List<Images>> getRolesEmployee(){
        return new ResponseEntity<>(imageService.getAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Images> createCategory(@RequestBody ImageDTO imageDTO){
        return new ResponseEntity<>(imageService.save(new Images(imageDTO)), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Images> updateRolesEmployeeById(@RequestBody ImageDTO imageDTO) throws CustomNotFoundException {
        
        return new ResponseEntity<>(imageService.save(new Images(imageDTO)), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteimageById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        this.imageService.deleteById(id);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Images>> getByProductId(@PathVariable(name = "id") Long productId){
        return new ResponseEntity<>(imageService.getImagesByProductId(productId), HttpStatus.OK);
    }
}
