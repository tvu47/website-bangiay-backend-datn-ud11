package com.snackman.datnud11.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class Count {
    Long count;
    Long productId;
    Long billDetailId;

    //This constructor will be used by Spring Data JPA
    //for creating this class instances as per result set
    public Count(Long count,Long productId, Long billDetailId){
        this.count = count;
        this.productId = productId;
        this.billDetailId = billDetailId;
    }
}