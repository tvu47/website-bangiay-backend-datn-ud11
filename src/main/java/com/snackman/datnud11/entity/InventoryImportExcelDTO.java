package com.snackman.datnud11.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryImportExcelDTO {
    private Long productDatailId;
    private String sku;
    private Long productId;
    private Long color;
    private Long size;
    private Integer quantity;
    private double price;
    private String image;
}
