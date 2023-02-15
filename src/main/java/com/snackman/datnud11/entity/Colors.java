package com.snackman.datnud11.entity;

import com.snackman.datnud11.dto.ColorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id", nullable = false)
    private Long id;

    @Column(name = "color_name")
    private String colorName;

    @Column(name ="status")
    private Boolean status;

    @Column(name = "product_id")
    private Long productId;

    public Colors(ColorDTO colorDTO){
        this.id = colorDTO.getId();
        this.colorName = colorDTO.getName();
        this.status = colorDTO.getStatus();
        this.productId = colorDTO.getProductId();
    }
}