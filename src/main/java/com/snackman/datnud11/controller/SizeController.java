package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.SizeDTO;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.entity.Size;
import com.snackman.datnud11.repo.SizeRepository;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.ProductService;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.services.SizeService;
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
    private SizeService sizeService;
    @Autowired
    private ProductService productService;
    
    @GetMapping("get-all")
    public ResponseEntity<List<Size>> getAll(){
        return new ResponseEntity<>(this.sizeService.findAll(), HttpStatus.OK);
    }
    @PostMapping("create")
    public ResponseEntity<Size> create(@RequestBody SizeDTO sizeDTO) throws Exception {
        Size size = new Size();
        size.setId(sizeDTO.getId());
        size.setSizeName(sizeDTO.getSizeName());
        size.setActiveStatus(sizeDTO.getActiveStatus());
        Products products = this.productService.findById(sizeDTO.getProductId());
        size.setProduct(products);
        System.out.println(size.toString());
        return new ResponseEntity<>(this.sizeService.save(size), HttpStatus.CREATED);
    }
    @PutMapping("update")
    public ResponseEntity<Size> updateRolesEmployeeById(@RequestBody Size size ) throws CustomNotFoundException {
        return null;
//        return new ResponseEntity<>(sizeRepository.save(size), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletesizeById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{       
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
