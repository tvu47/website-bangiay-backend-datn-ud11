package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "images")
@Data
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;
    @Column(name = "name_image")
    private String imageName;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name ="status")
    private boolean status;

}