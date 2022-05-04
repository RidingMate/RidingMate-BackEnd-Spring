package com.ridingmate.api.payload.common;

import com.ridingmate.api.consts.ResponseCode;
import lombok.Data;

@Data
public class ApiResponse extends ResponseDto {

    public ApiResponse(ResponseCode response) {
        this.response = response;
    }
}
