package com.ridingmate.api.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.BindingErrorDto;
import com.ridingmate.api.payload.common.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
//응답을 json으로 내려준다.
public class GlobalControllerAdvice {

    private ResponseEntity<ResponseDto<?>> response(ResponseCode code, Object response) {
        return ResponseEntity.status(code.getResponseCode())
                             .body(ResponseDto.builder()
                                              .response(response)
                                              .responseCode(code)
                                              .build());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        return response(e.getErrorCode(), null);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> onBindException(BindException e) {
        List<BindingErrorDto> errorList = e.getFieldErrors().stream()
                                           .map(error -> BindingErrorDto.builder()
                                                   .field(error.getField())
                                                   .message(error.getDefaultMessage())
                                                                        .build())
                                           .collect(Collectors.toList());
        return response(ResponseCode.INVALID_PARAMETER, errorList);
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
