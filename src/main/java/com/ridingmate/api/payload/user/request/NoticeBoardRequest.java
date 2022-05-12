package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class NoticeBoardRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    @ApiModelProperty(value = "공지사항 게시글 제목", required = true)
    private String title;
}
