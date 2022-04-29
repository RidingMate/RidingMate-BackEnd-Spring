package com.ridingmate.api.payload;

import lombok.Getter;

@Getter
public class AuthResponse extends ResponseDto {

    private String accessToken;

    public AuthResponse(String message) {
        setMessage(message);
    }

    public AuthResponse(String accessToken, String message) {
        this.accessToken = accessToken;
        setMessage(message);
    }
}
