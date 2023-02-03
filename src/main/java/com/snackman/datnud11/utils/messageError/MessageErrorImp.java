package com.snackman.datnud11.utils.messageError;

import org.springframework.stereotype.Service;

@Service
public class MessageErrorImp implements MessageError {
    public static final String ERROR_NULL_BY_FIND_ID="Id doesn't found in database ";

    @Override
    public String getMessageWhenFindByIDNull(String id) {
        return ERROR_NULL_BY_FIND_ID+id;
    }
}
