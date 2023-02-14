package com.snackman.datnud11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeDTO {

    private Long id;

    private Long productId;

    private String sizeName;

    private Boolean activeStatus;

}
