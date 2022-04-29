package com.ridingmate.api.payload;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class NormalLoginRequest {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;
}
