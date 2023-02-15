package com.snackman.datnud11.entity;

import com.snackman.datnud11.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name ="status")
    private Boolean status;

    public Category(CategoryDTO categoryDTO){
        this.id = categoryDTO.getId();
        this.categoryName = categoryDTO.getCategoryName();
        this.status = categoryDTO.getStatus();
    }
}