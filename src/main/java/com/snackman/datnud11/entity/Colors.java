package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "colors")
@Data
public class Colors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id", nullable = false)
    private Long id;
    @Column(name = "color_name")
    private String colorName;
    @Column(name ="status")
    private boolean status;
}