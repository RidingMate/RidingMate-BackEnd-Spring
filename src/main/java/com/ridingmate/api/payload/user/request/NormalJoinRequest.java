package com.ridingmate.api.payload.user.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class NormalJoinRequest {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String nickname;
}
