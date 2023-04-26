package com.snackman.datnud11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {

    private Long id;
    private String name;
    private String content;
    private Long category;
    private Long material;
    private String manufactory;

}
