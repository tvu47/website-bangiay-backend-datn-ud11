package com.snackman.datnud11.consts;

public enum BillStatus {

    CHO_DUYET(0),
    DANG_GIAO(1),
    DA_HUY(2),
    DA_GIAO(3),
    DANG_DOI_TRA(4),
    DA_DUYET(5);

    BillStatus(int status){
        this.status = status;
    }

    public int status;
}
