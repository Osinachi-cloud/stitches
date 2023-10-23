package com.stitches.exceptions;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final String API_UNHANDLED_EXCEPTION = "error: %s";
    private static final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

    private static final HttpStatus methodNotAllowedError = HttpStatus.METHOD_NOT_ALLOWED;

    private static final HttpStatus badRequestError = HttpStatus.BAD_REQUEST;

    private static final HttpStatus accessDeniedError = HttpStatus.FORBIDDEN;


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception e) {
        ApiException exceptionDetail = new ApiException(
                String.format(API_UNHANDLED_EXCEPTION, e.getMessage()),
                internalServerError,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<Object>(exceptionDetail, internalServerError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        ApiException exceptionDetail = new ApiException(
                "Method not allowed: " + e.getMethod(),
                methodNotAllowedError,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(exceptionDetail, methodNotAllowedError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredential(BadCredentialsException e) {
        ApiException exceptionDetail = new ApiException(
                "Bad Credentials: " + e.getMessage(),
                badRequestError,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(exceptionDetail, badRequestError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDenied(AccessDeniedException e) {
        ApiException exceptionDetail = new ApiException(
                "Access Denied: " + e.getMessage(),
                accessDeniedError,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(exceptionDetail, accessDeniedError);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> accessDenied(SignatureException e) {
        ApiException exceptionDetail = new ApiException(
                "Access Denied: " + e.getMessage(),
                accessDeniedError,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(exceptionDetail, accessDeniedError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> expiredJwt(JwtException e) {
        ApiException exceptionDetail = new ApiException(
                "Access Denied: " + e.getMessage(),
                accessDeniedError,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(exceptionDetail, accessDeniedError);
    }


    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiException exceptionDetail = new ApiException(
                String.format(API_UNHANDLED_EXCEPTION, ex.getMessage()),
                internalServerError,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<Object>(exceptionDetail, internalServerError);
    }

}



