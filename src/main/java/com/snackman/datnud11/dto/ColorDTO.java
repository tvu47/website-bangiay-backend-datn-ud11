package com.snackman.datnud11.dto;

import com.snackman.datnud11.entity.Colors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColorDTO {

    private Long id;
    private String name;
    private Boolean status;

    public Colors convertToColors(){
        Colors colors = new Colors();
        colors.setId(this.id);
        colors.setColorName(this.name);
        colors.setStatus(this.status);
        return colors;
    }
}
