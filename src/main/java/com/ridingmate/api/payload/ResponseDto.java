package com.ridingmate.api.payload;

import com.ridingmate.api.consts.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ResponseDto {

    public ResponseCode response;
}
