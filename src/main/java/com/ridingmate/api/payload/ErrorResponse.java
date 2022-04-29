package com.ridingmate.api.payload;

import lombok.Getter;

@Getter
public class ErrorResponse extends ResponseDto {

    public ErrorResponse(String message) {
        setMessage(message);
    }
}
