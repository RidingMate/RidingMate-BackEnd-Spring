package com.ridingmate.api.payload.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "인증용 응답 객체")
public class AuthResponse {

    @ApiModelProperty(value = "Bearer 토큰 값")
    private String accessToken;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
