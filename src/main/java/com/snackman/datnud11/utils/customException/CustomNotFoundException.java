package com.snackman.datnud11.utils.customException;

public class CustomNotFoundException extends ClassNotFoundException {
    public CustomNotFoundException(String message){
        super(message);
    }
}
