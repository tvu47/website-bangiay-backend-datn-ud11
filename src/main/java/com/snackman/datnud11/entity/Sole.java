package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sole")
@Data
public class Sole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sole_id", nullable = false)
    private Long id;
    @Column(name = "name_sole")
    private String soleName;
    @Column(name = "properties")
    private String properties;
    @Column(name = "status")
    private boolean status;
}