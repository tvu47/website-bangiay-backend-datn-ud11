package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.ColorDTO;
import com.snackman.datnud11.entity.Colors;
import com.snackman.datnud11.services.ColorService;
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
    private ColorService colorService;
    
    @GetMapping()
    public ResponseEntity<List<Colors>> getColors(){
        return new ResponseEntity<>(colorService.findAll(), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Colors> createColor(@RequestBody ColorDTO colorDTO){
        Colors color = new Colors(colorDTO);
        return new ResponseEntity<>(colorService.save(color), HttpStatus.CREATED);
    }
    @PutMapping()
    public ResponseEntity<Colors> updateColor(@RequestBody ColorDTO colorDTO) throws CustomNotFoundException {
        Colors color = new Colors(colorDTO);
        return new ResponseEntity<>(colorService.save(color), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteColorById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        try {
            Colors color = this.colorService.findById(id);
            this.colorService.delete(color);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok("Delete successfully!");
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Colors>> getColorsByProductId(@PathVariable(name = "id") Long productId){
        return new ResponseEntity<>(this.colorService.findByProductId(productId),HttpStatus.OK);
    }
}
