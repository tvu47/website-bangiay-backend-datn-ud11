package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "materials")
@Data
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id", nullable = false)
    private Long id;
    @Column(name = "material_name")
    private String materialName;
    @Column(name = "import_address")
    private String importAddress;
    @Column(name = "import_date")
    private Date importDate;
    @Column(name ="status")
    private boolean status;
}