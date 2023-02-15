package com.snackman.datnud11.dto;

import com.snackman.datnud11.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    private String categoryName;

    private Boolean status;

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.categoryName =category.getCategoryName();
        this.status = category.getStatus();
    }
}
