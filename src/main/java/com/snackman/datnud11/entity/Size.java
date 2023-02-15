package com.snackman.datnud11.entity;

import com.snackman.datnud11.dto.SizeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "size")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "size_name")
    private String sizeName;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "product_id")
    private Long productId;

    public Size(SizeDTO sizeDTO){
        this.id = sizeDTO.getId();
        this.sizeName = sizeDTO.getSizeName();
        this.productId = sizeDTO.getProductId();
        this.activeStatus = sizeDTO.getActiveStatus();
    }
}