package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Card;
import com.snackman.datnud11.repo.CardRepository;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImp implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Override
    public Card checkCardExist(Long id) throws CustomNotFoundException {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (cardOptional.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return cardOptional.get();
    }
}
