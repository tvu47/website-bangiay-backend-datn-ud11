package com.snackman.datnud11.dto;

import com.snackman.datnud11.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Long id;

    private Long productId;

    private String imageUrl;

    private Boolean status;

    public ImageDTO(Images images){
        this.id = images.getId();
        this.productId = images.getProductId();
        this.imageUrl = images.getImageUrl();
        this.status = images.getStatus();
    }
}
