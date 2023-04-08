package com.snackman.datnud11.controller.errors;

import com.snackman.datnud11.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = {RestController.class})
public class HandleControllerException {
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({CustomMessageException.class})
    public Map<String, String> handle_Errors(CustomMessageException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({CommonErrorException.class})
    public Map<String, String> handle_Common_Errors(CommonErrorException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class})
    public Map<String, String> handle_All_Errors(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error_message", ex.getMessage());
        return errorMap;
    }
}
