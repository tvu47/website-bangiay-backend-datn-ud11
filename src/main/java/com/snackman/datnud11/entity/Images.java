package com.snackman.datnud11.entity;

import com.snackman.datnud11.dto.ImageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name ="status")
    private Boolean status;

    public Images(ImageDTO imageDTO){
        this.id = imageDTO.getId();
        this.productId = imageDTO.getProductId();
        this.imageUrl = imageDTO.getImageUrl();
        this.status = imageDTO.getStatus();
    }
}