package com.stitches.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ApiException {
    private  String message;
    private  HttpStatus httpStatus;
    private  ZonedDateTime timeStamp;
}
