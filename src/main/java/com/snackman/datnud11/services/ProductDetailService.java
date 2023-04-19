package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.ProductDetailDTO;
import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.ProductDetail;
import com.snackman.datnud11.responses.ProductDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductDetailService {

    ProductDetail save(ProductDetail productDetail);

    ProductDetail findBySku(String sku);

    List<ProductDetail> findByProductId(Long id);

    List<ProductDetail> findAll();

    List<ProductDetail> findByProductId(List<ProductDetail> productDetailList, Long id);

    ProductDetailResponse getProductById(Long id) throws Exception;

    boolean validProductsOrder(List<PaymentDTO.ProductOrder> productOrders);

    ProductDetail save(ProductDetailDTO productDetailDTO) throws Exception;

    void saveInventoryToDatabase(MultipartFile multipartFile);

}
