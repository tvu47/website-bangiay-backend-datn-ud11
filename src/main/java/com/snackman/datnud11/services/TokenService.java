package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.TokenJwt;
import com.snackman.datnud11.exceptions.JwtTokenException;

public interface TokenService {
    TokenJwt saveToken(TokenJwt token) throws JwtTokenException;
    TokenJwt getTokenByUsername(String username);
}
