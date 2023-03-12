package com.snackman.datnud11.dto.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@Builder
public class LoginError {
    private String message;
    private HttpStatus httpStatus;
    private String title;
}
