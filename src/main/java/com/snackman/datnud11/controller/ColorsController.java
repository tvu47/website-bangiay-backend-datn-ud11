package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.ColorDTO;
import com.snackman.datnud11.entity.Colors;
import com.snackman.datnud11.repo.ColorsRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.ColorServiceImp;
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
    private ColorServiceImp colorServiceImp;
    
    @GetMapping("get-all")
    public ResponseEntity<List<Colors>> getColors(){
        return new ResponseEntity<>(colorServiceImp.findAll(), HttpStatus.OK);
    }
    @PostMapping("create")
    public ResponseEntity<Colors> createColor(@RequestBody ColorDTO colorDTO){
        Colors color = colorDTO.convertToColors();
        return new ResponseEntity<>(colorServiceImp.save(color), HttpStatus.CREATED);
    }
    @PutMapping("update")
    public ResponseEntity<Colors> updateColor(@RequestBody ColorDTO colorDTO) throws CustomNotFoundException {
        Colors color = colorDTO.convertToColors();
        return new ResponseEntity<>(colorServiceImp.save(color), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteColorById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        try {
            Colors color = this.colorServiceImp.findById(id);
            this.colorServiceImp.delete(color);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok("Delete successfully!");
    }
}
