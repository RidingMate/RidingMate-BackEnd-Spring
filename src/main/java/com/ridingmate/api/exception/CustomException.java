package com.ridingmate.api.exception;

import com.ridingmate.api.consts.ResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ResponseCode errorCode;

    public CustomException(ResponseCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
