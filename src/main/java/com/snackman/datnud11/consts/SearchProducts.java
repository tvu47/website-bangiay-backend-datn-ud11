package com.snackman.datnud11.consts;

public enum SearchProducts {

    FIND_BY_CATEGORY_ID("categoryId"),
    FIND_BY_PRODUCT_NAME("name"),
    FIND_BY_PRICE_RANGE("priceRange"),
    ORDER_BY_NAME("sortName"),
    ORDER_BY_PRICE("sortPrice")
    ;


    public String paramName;

    SearchProducts(String paramName){
        this.paramName = paramName;
    }

}
