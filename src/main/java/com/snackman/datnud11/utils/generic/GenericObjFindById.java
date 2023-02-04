package com.snackman.datnud11.utils.generic;

import com.snackman.datnud11.utils.customException.CustomNullException;
import com.snackman.datnud11.utils.messageError.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class GenericObjFindById<T> {
    @Autowired
    MessageError messageError;

    public GenericObjFindById(){

    }

    public <T> T findByIdObject(Optional<T> t) {
        if (t.isEmpty()){
            throw new CustomNullException("Id doesn't found in database");
        }
        return t.get();
    };
}
