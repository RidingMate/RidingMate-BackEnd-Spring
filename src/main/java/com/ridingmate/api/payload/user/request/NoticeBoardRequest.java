package com.ridingmate.api.payload.user.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class NoticeBoardRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;
}
