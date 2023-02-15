package com.snackman.datnud11.dto;

import com.snackman.datnud11.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeDTO {

    private Long id;

    private Long productId;

    private String sizeName;

    private Boolean activeStatus;

    public SizeDTO(Size size){
        this.id = size.getId();
        this.productId = size.getProductId();
        this.sizeName = size.getSizeName();
        this.activeStatus = size.getActiveStatus();
    }

}
