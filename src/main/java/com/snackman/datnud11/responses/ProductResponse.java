package com.snackman.datnud11.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String content;
    private Boolean status;
    private String category;
    private String material;
    private String manufactory;
}
