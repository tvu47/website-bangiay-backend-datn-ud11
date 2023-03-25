package com.snackman.datnud11.controller.errors;

import com.snackman.datnud11.dto.error.LoginError;
import com.snackman.datnud11.exceptions.BadLoginException;
import com.snackman.datnud11.exceptions.BadRequestException;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = {RestController.class})
public class LoginControllerException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class})
    public Map<String, String> handleLoginValidErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());});
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserExistedException.class})
    public Map<String, String> handleRegisterErrors(UserExistedException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserNotfoundException.class})
    public Map<String, String> handleLoginForAdmin_Errors(UserNotfoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadLoginException.class})
    public Map<String, String> handleBadLogin_Errors(BadLoginException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public Map<String, String> handleBadRequest_Errors(BadRequestException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ExpiredJwtException.class})
    public Map<String, String> handleExpireToken_Errors(ExpiredJwtException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }
}
