package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.ProductDetailDTO;
import com.snackman.datnud11.entity.ProductDetail;
import com.snackman.datnud11.responses.ProductDetailResponse;
import com.snackman.datnud11.responses.NoticeResponse;
import com.snackman.datnud11.services.ProductDetailService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping
    public ResponseEntity<List<ProductDetail>> getRolesEmployee(){
//        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
        return null;
    }
    @PostMapping
    public ResponseEntity<ProductDetail> createCategory(@RequestBody ProductDetail productDetail){
//        return new ResponseEntity<>(inventoryRepository.save(inventory), HttpStatus.CREATED);
        return null;
    }
    @PutMapping
    public ResponseEntity<ProductDetail> updateRolesEmployeeById(@RequestBody ProductDetail productDetail) throws CustomNotFoundException {

//        return new ResponseEntity<>(inventoryRepository.save(inventory), HttpStatus.CREATED);
        return null;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteinventoryById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> findByProductId(@PathVariable(name = "id") Long id) throws Exception {
        return new ResponseEntity<>(this.productDetailService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/store")
    public ResponseEntity<NoticeResponse> storeInventory(@RequestBody ProductDetailDTO productDetailDTO) throws Exception{
        ProductDetail productDetail = this.productDetailService.save(productDetailDTO);
        return new ResponseEntity<>(new NoticeResponse(HttpStatus.OK.value(), "Thêm thành công!", productDetail), HttpStatus.OK);
    }
    @PostMapping(value = "/upload-data", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFileExcel(@RequestParam(value = "file")MultipartFile file){
      if (file.isEmpty()){
        return new ResponseEntity<>("Import data fail! ", HttpStatus.OK);
      }else{
        this.productDetailService.saveInventoryToDatabase(file);
        return new ResponseEntity<>("Import successfully!", HttpStatus.OK);
      }
    }
}
