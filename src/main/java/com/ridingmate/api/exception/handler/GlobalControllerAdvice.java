package com.ridingmate.api.exception.handler;

import com.ridingmate.api.payload.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ErrorResponse nullPointerException(NullPointerException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse illegalStateException(IllegalStateException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ErrorResponse authenticationException(AuthenticationException e) {
//        log.error(e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }
}