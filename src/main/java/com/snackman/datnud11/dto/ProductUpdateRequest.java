package com.snackman.datnud11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String content;
    private String category;
    private String material;
    private String manufactory;
    private String images;
}
