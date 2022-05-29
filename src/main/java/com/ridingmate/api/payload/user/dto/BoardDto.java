package com.ridingmate.api.payload.user.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class BoardDto {

    public static class Request {

        @Data
        public static class TradeList {
            @ApiModelProperty(value = "제조사")
            private String company;

            @ApiModelProperty(value = "모델명")
            private String modelName;

            @ApiModelProperty(value = "지역코드")
            private String locationCode;

            @ApiModelProperty(value = "주행거리 최소값")
            private int minMileage = 0;

            @ApiModelProperty(value = "주행거리 최대값")
            private int maxMileage = 999999999;

            @ApiModelProperty(value = "가격 최소값")
            private int minPrice = 0;

            @ApiModelProperty(value = "가격 최대값")
            private int maxPrice = 999999999;

            @ApiModelProperty(value = "연식 최소값")
            private int minYear = 0;

            @ApiModelProperty(value = "연식 최대값")
            private int maxYear = 999999999;

            @ApiModelProperty(value = "배기량 최소값")
            private int minCc = 0;

            @ApiModelProperty(value = "배기량 최대값")
            private int maxCc = 999999999;

            @ApiModelProperty(value = "거래 완료 안보기 체크 Y / N")
            @Pattern(regexp = "^[Y|N]{1}$", message = "Y와 N만 입력 가능합니다.")
            private String hideComplete = "N";
        }

        @Data
        public static class TradeInsert {
            @NotEmpty(message = "제목은 필수입니다.")
            @ApiModelProperty(value = "거래글 제목", required = true)
            private String title;

            @ApiModelProperty(value = "가격")
            @Min(0)
            private int price;

            @ApiModelProperty(value = "업체명")
            private String company;

            @ApiModelProperty(value = "모델명")
            private String modelName;

            @ApiModelProperty(value = "연비")
            private double fuelEfficiency;

            @ApiModelProperty(value = "cc")
            private int cc;

            @ApiModelProperty(value = "연식")
            private int year;

            @ApiModelProperty(value = "주행거리")
            @Min(0)
            private int mileage;

            @ApiModelProperty(value = "지역 코드")
            private String locationCode;

            @ApiModelProperty(value = "구매 일자 연도")
            private Integer purchaseYear;

            @ApiModelProperty(value = "구매 일자 월")
            private Integer purchaseMonth;

            @ApiModelProperty(value = "상세 내용")
            private String content;

            @ApiModelProperty(value = "연락처")
            private String phoneNumber;

            @ApiModelProperty(value = "구매자에게 주유/정비 정보 공개하기 Y/N")
            @Pattern(regexp = "^[Y|N]{1}$", message = "Y와 N만 입력 가능합니다.")
            private String isOpenToBuyer;
        }

        @Data
        public static class NoticeInsert {
            @NotEmpty(message = "제목은 필수입니다.")
            @ApiModelProperty(value = "공지사항 게시글 제목", required = true)
            private String title;
        }
    }

    public static class Response {

        @Getter
        @Builder
        public static class TradeList {
            private Long id;
            private String title;
            private String modelName;
            private String company;
            private int mileage;
            private int cc;
            private int year;
            private int price;
        }

        @Getter
        @Builder
        public static class TradeContent {
            private String title;
            private String modelName;
            private String company;
            private int mileage;
            private int cc;
            private int year;
            private int price;
            private String location;
            private String dateOfPurchase;
        }

        @Getter
        @Builder
        public static class NoticeList {
            private Long id;

            private String title;

            private String date;
        }

        @Getter
        @Builder
        public static class NoticeContent {
            private String title;
        }


    }
}
