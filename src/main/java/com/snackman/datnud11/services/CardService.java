package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Card;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface CardService {
    Card checkCardExist(Long id) throws CustomNotFoundException;
}
