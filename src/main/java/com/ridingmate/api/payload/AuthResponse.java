package com.ridingmate.api.payload;

import lombok.Getter;

@Getter
public class AuthResponse extends ResponseDto {

    public AuthResponse(String message) {
        setMessage(message);
    }
}
