package com.snackman.datnud11.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Count {
    private Long idd;
    private long count;

    //This constructor will be used by Spring Data JPA
    //for creating this class instances as per result set
    public Count(long count,Long idd){
        this.idd = idd;
        this.count = count;
    }
}