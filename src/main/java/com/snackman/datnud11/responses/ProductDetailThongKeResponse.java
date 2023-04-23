package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.ProductDetail;
import com.snackman.datnud11.entity.Products;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDetailThongKeResponse {
//    private Long id;
//    private Products product;
//    private int quatity;
//    private double price;
//    private Date importTime;
//    private String image;
//
//    private String sku;
//    private String colorName;
//    private String sizeName;

    private Long totalQuantity;
    private Double amount;
    private List<ProductRes> products;

    public ProductDetailThongKeResponse( Long totalQuantity, Double amount) {
        this.totalQuantity = totalQuantity;
        this.amount = amount;
    }
}
