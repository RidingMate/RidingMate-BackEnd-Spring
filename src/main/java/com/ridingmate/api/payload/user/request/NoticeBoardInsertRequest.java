package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeBoardInsertRequest {

    @ApiModelProperty(value = "공지사항 게시글 제목", required = true)
    private String title;
}
