package com.stitches.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiException {
    private  String message;
    private  HttpStatus httpStatus;
    private  ZonedDateTime timeStamp;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
}
