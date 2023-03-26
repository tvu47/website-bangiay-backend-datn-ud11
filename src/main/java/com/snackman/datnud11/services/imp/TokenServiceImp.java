package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.TokenJwt;
import com.snackman.datnud11.exceptions.JwtTokenException;
import com.snackman.datnud11.repo.TokenJwtRepo;
import com.snackman.datnud11.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenServiceImp implements TokenService {
    @Autowired
    private TokenJwtRepo tokenJwtRepo;

    public TokenJwt saveToken(TokenJwt token) throws JwtTokenException {
        TokenJwt tokenJwt = tokenJwtRepo.save(token);
        if (tokenJwt == null){
            throw new JwtTokenException("token is not save");
        }
        return tokenJwt;
    }

    @Override
    public TokenJwt getTokenByUsername(String username) {
        return tokenJwtRepo.findTokenJwtByUsernameAndExpiress(username);
    }
}
