package com.snackman.datnud11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {

    private Long productId;
    private String color;
    private String size;
    private Integer quantity;
    private Integer price;
    private String image;

}
