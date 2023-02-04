package com.snackman.datnud11.utils.generic;

import com.snackman.datnud11.utils.customException.CustomNullException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@NoArgsConstructor
public class GenericObjFindById<T> {

    public <T> T findByIdObject(Optional<T> t) {
        if (t.isEmpty()){
            throw new CustomNullException(ErrorMessage.ERROR_MESSAGE_NULL.toString());
        }
        return t.get();
    };
}
