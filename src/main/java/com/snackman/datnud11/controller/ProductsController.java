package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.ProductDTO;
import com.snackman.datnud11.dto.ProductSame;
import com.snackman.datnud11.dto.ProductUpdateRequest;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.responses.NoticeResponse;
import com.snackman.datnud11.responses.ProductManagerResponse;
import com.snackman.datnud11.responses.ProductResponse;
import com.snackman.datnud11.responses.ProductsResponse;
import com.snackman.datnud11.services.ProductService;
import com.snackman.datnud11.services.ZProductService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductsController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ZProductService zProductService;


    @GetMapping
    public ResponseEntity<List<ProductsResponse>> getAll(){
        System.out.println("product is getting data...");
        return new ResponseEntity<>(this.zProductService.getAllProductResponses(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<NoticeResponse> createProduct(@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(new NoticeResponse(HttpStatus.OK.value(),"Thêm mới sản phẩm thành công",this.productService.save(productDTO)), HttpStatus.CREATED);
    }
    @PutMapping
    @PreAuthorize("CLIENT_ROLE")
    public ResponseEntity<Products> updateProduct(@RequestBody ProductUpdateRequest product ){
        log.info("product update: {}", product);
        return new ResponseEntity<>(this.productService.updateProduct(product), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductsResponse>> searchProducts(@RequestBody Map<String,List<String>> params) throws Exception {
        return new ResponseEntity<>(this.zProductService.formatProductToProductResponse(this.productService.searchProducts(params)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductsResponse>> findById(@PathVariable(name = "id") Long id) throws Exception {
        return new ResponseEntity<>(this.zProductService.findByProductId(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductManagerResponse>> findAllProductManager() throws Exception{
        return new ResponseEntity<>(this.productService.findAllProductsManager(), HttpStatus.OK);
    }

    @GetMapping("/list-all-manager")
    public ResponseEntity<List<ProductResponse>> listAllManager() throws Exception{
        return new ResponseEntity<>(this.productService.listAllProductManager(), HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importFileExcel(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/best-sell-products")
    public ResponseEntity<List<ProductsResponse>> getBestSellProducts(){
        return new ResponseEntity<>(this.zProductService.getBestSellProducts(), HttpStatus.OK);
    }
    @GetMapping("/newest-products")
    public ResponseEntity<List<ProductsResponse>> getNewestProduct(){
        return new ResponseEntity<>(this.zProductService.getNewestProducts(), HttpStatus.OK);
    }

    @PostMapping("/same-product-category")
    public ResponseEntity<List<ProductsResponse>> getSameProductCategory(@RequestBody ProductSame productSame){
        return new ResponseEntity<>(this.zProductService.getSameProducts(productSame.getCategoryId(), productSame.getProductId()), HttpStatus.OK);
    }

    @PostMapping("/delete-product")
    public ResponseEntity<NoticeResponse> deleteProduct(@RequestParam(value = "id", required = true) Long id){
        this.productService.delete(id);
        return new ResponseEntity<>(new NoticeResponse(200,"Xóa thành công",null), HttpStatus.OK);
    }

}

