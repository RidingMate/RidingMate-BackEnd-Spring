package com.ridingmate.api.payload;

import com.ridingmate.api.consts.ResponseCode;
import lombok.Getter;

@Getter
public class AuthResponse extends ResponseDto {

    private String accessToken;

    public AuthResponse(ResponseCode response) {
        this.response = response;
    }

    public AuthResponse(String accessToken, ResponseCode response) {
        this.accessToken = accessToken;
        this.response = response;
    }
}
