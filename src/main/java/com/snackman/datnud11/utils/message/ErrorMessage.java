package com.snackman.datnud11.utils.message;

public enum ErrorMessage {
    ERROR_MESSAGE_NOT_FOUND("DATA NOT FOUND"),
    ERROR_MESSAGE_WRONG_FORMAT("DATA FORMAT IS WRONG"),
    ERROR_MESSAGE_NULL("DATA IS NULL"),
    ;

    private final String error;

    ErrorMessage(final String error){
        this.error = error;
    }

    @Override
    public String toString(){
        return error;
    }
}
