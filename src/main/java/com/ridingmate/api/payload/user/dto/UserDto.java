package com.ridingmate.api.payload.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

public class UserDto {

    public static class Response {

        @Data
        @Builder
        @ApiModel(description = "활동 카운트")
        public static class Count {
            private int tradeCount;
            private int commentCount;
            private int bookmarkCount;
        }
    }
}
