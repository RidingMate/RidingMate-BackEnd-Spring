package com.ridingmate.api.exception;

import lombok.Getter;

@Getter
public class ParameterException extends RuntimeException {

    public ParameterException(String message) {
        super(message);
    }

}
