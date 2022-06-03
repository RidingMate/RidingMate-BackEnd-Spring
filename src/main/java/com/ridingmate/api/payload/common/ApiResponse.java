package com.ridingmate.api.payload.common;

import com.ridingmate.api.consts.ResponseCode;

import lombok.Data;

@Data
public class ApiResponse {

    private ResponseCode response;

    public ApiResponse(ResponseCode response) {
        this.response = response;
    }
}
