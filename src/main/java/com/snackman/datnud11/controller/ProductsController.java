package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.ProductDTO;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.responses.ProductsResponse;
import com.snackman.datnud11.services.ProductService;
import com.snackman.datnud11.services.ZProductService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ZProductService zProductService;

    @GetMapping
    public ResponseEntity<List<ProductsResponse>> getAll(){
        return new ResponseEntity<>(this.zProductService.getAllProductResponses(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(this.productService.save(new Products(productDTO)), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Products> updateProduct(@RequestBody Products product ) throws CustomNotFoundException {
        return new ResponseEntity<>(this.productService.save(product), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Products>> searchProducts(@RequestBody Map<String,List<String>> params) throws Exception {
        return new ResponseEntity<>(this.productService.searchProducts(params), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductsResponse>> findById(@PathVariable(name = "id") Long id) throws Exception {
        return new ResponseEntity<>(this.zProductService.findByProductId(id), HttpStatus.OK);
    }
}

