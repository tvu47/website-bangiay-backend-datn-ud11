package com.snackman.datnud11.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class NoticeResponse {

    private Integer code;
    private String content;
    private Object object;
}
