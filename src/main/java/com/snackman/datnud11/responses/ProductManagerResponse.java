package com.snackman.datnud11.responses;

import lombok.Data;

import java.util.List;

@Data
public class ProductManagerResponse {

    private Long id;
    private String name;
    private String content;
    private Boolean status;
    private String category;
    private String material;
    private String manufactory;
    private List<Inventory> inventories;

    @Data
    public static class Inventory{

        private Long id;
        private String sku;
        private String color;
        private String size;
        private Integer quantity;
        private Double price;

    }

}
