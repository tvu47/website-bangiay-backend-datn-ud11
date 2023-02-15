package com.snackman.datnud11.dto;

import com.snackman.datnud11.entity.Colors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColorDTO {

    private Long id;
    private String name;
    private Boolean status;

    private Long productId;

    public ColorDTO(Colors colors){
        this.id = colors.getId();
        this.name = colors.getColorName();
        this.status = colors.getStatus();
        this.productId = colors.getProductId();
    }
}
