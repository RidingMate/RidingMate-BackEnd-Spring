package com.ridingmate.api.exception.handler;

import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.exception.ParameterException;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.common.ParameterErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> customException(CustomException e) {
        return ResponseEntity.badRequest().body(new ApiResponse(e.getErrorCode()));
    }

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<ParameterErrorResponse> parameterException(ParameterException e) {
        return ResponseEntity.badRequest().body(new ParameterErrorResponse(400, e.getMessage()));
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ErrorResponse nullPointerException(NullPointerException e) {
//        log.error(e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    public ErrorResponse illegalStateException(IllegalStateException e) {
//        log.error(e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ErrorResponse authenticationException(AuthenticationException e) {
//        log.error(e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }
}
