package com.ridingmate.api.payload.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class CommentDto {

    public static class Request {

        @Data
        @ApiModel(description = "댓글 등록 dto")
        public static class InsertComment {
            @ApiModelProperty("댓글 내용")
            private String content;

            @ApiModelProperty("게시글 번호")
            private Long boardId;
        }

        @Data
        @ApiModel(description = "대댓글 등록 dto")
        public static class InsertReply {

            @ApiModelProperty("상위 댓글 번호")
            private Long commentId;

            @ApiModelProperty("댓글 내용")
            private String content;

            @ApiModelProperty("게시글 번호")
            private Long boardId;
        }
    }

    public static class Response {
        @Data
        @Builder
        @ApiModel("댓글 조회 dto")
        public static class Info {
            @ApiModelProperty("댓글 id - 대댓글 조회, 등록시 사용")
            private Long commentId;

            @ApiModelProperty("댓글 내용")
            private String content;

            @ApiModelProperty("댓글 작성자")
            private String username;

            @ApiModelProperty("댓글 작성 날짜")
            private String date;
        }
    }
}
