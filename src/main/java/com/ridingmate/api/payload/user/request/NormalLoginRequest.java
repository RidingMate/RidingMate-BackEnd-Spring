package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class NormalLoginRequest {

    @NotEmpty(message = "아이디는 필수입니다.")
    @ApiModelProperty(value = "유저 ID", required = true)
    private String userId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;
}
