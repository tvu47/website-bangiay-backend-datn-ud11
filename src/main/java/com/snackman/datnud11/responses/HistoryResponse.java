package com.snackman.datnud11.responses;

import lombok.Data;

@Data
public class HistoryResponse {
    private String nameProduct;
    private String image;
    private String typeProduct;
    private int quatity;
    private double amoutProduct;
    private double amoutPay;
    private int billStatus;
}
