package com.ridingmate.api.payload;

import com.ridingmate.api.consts.ResponseCode;
import lombok.Data;

@Data
public class ApiResponse extends ResponseDto {

    public ApiResponse(ResponseCode response) {
        this.response = response;
    }
}
