package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.SocialUserEntity;
import com.ridingmate.api.entity.value.SocialType;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

public class UserDto {

    public static class Response {

        @Data
        @Builder
        @ApiModel(description = "유저 정보")
        public static class Info {
            private SocialType socialType;
            private String nickname;
            private String phoneNumber;

            public static Info of(NormalUserEntity user) {
                return builder()
                        .nickname(user.getNickname())
                        .phoneNumber(user.getPhoneNumber())
                        .build();
            }

            public static Info of(SocialUserEntity user) {
                return builder()
                        .socialType(user.getSocialType())
                        .nickname(user.getNickname())
                        .phoneNumber(user.getPhoneNumber())
                        .build();
            }
        }

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
