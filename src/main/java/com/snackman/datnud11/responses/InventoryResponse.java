package com.snackman.datnud11.responses;

import com.snackman.datnud11.entity.Colors;
import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.entity.Size;
import com.snackman.datnud11.services.InventoryService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryResponse {

    private ProductsResponse products;
    private List<ColorOption> colorOptions= new ArrayList<>();

    @Data
    public static class ColorOption{
        private Colors colors;
        private List<SizeOption> sizeOptions = new ArrayList<>();
    }

    @Data
    public static class SizeOption{
        private Size size;
        private Integer quantity;
        private Double price;
        private String imageUrl;
    }

}
