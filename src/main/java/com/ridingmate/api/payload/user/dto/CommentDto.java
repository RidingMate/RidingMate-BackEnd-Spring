package com.ridingmate.api.payload.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class CommentDto {

    public static class Request {

        @Data
        @ApiModel(description = "댓글 등록 dto")
        public static class Insert {
            @ApiModelProperty("댓글 내용")
            private String content;

            @ApiModelProperty("게시글 번호")
            private Long boardId;
        }
    }
}
