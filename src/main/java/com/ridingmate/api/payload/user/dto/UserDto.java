package com.ridingmate.api.payload.user.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ridingmate.api.entity.UserEntity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

public class UserDto {

    public static class Request {

        @Data
        @ApiModel(description = "유저 정보 수정")
        public static class Update {
            private String nickname;
            private String phoneNumber;
            private MultipartFile profileFile;
        }
    }

    public static class Response {

        @Data
        @Builder
        @ApiModel(description = "활동 카운트")
        public static class Count {
            private int tradeCount;
            private int commentCount;
            private int bookmarkCount;
        }

        @Data
        @Builder
        @ApiModel(description = "유저 정보")
        public static class Info {
            private String nickName;
            private String phoneNumber;
            private String profileImageUrl;

            public static Info of(UserEntity user) {
                return builder()
                        .nickName(user.getNickname())
                        .phoneNumber(user.getPhoneNumber())
                        .profileImageUrl(user.getProfileImageUrl())
                        .build();
            }
        }
    }
}
