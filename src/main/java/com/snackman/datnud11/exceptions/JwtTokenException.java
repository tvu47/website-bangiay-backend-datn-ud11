package com.snackman.datnud11.exceptions;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class JwtTokenException extends Exception {
    public JwtTokenException(String message) {
        super(message);
    }
}
