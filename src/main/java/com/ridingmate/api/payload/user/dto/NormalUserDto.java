package com.ridingmate.api.payload.user.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class NormalUserDto {

    public static class Request {

        @Data
        @ApiModel(description = "일반 회원가입 요청 dto")
        public static class Join {
            @NotEmpty(message = "아이디는 필수입니다.")
            @ApiModelProperty(value = "유저 ID", required = true)
            private String userId;

            @NotEmpty(message = "비밀번호는 필수입니다.")
            @ApiModelProperty(value = "비밀번호", required = true)
            private String password;

            @NotEmpty(message = "닉네임은 필수입니다.")
            @ApiModelProperty(value = "닉네임", required = true)
            private String nickname;
        }

        @Data
        @ApiModel(description = "일반 로그인 요청 dto")
        public static class Login {
            @NotEmpty(message = "아이디는 필수입니다.")
            @ApiModelProperty(value = "유저 ID", required = true)
            private String userId;

            @NotEmpty(message = "비밀번호는 필수입니다.")
            @ApiModelProperty(value = "비밀번호", required = true)
            private String password;
        }
    }
}
