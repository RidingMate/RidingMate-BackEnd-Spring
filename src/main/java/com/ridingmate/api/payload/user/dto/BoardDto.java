package com.ridingmate.api.payload.user.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@ApiModel(description = "거래글 관련 dto")
public class BoardDto {

    @ApiModel(description = "거래글 요청 dto")
    public static class Request {

        @Data
        @ApiModel(description = "거래글 리스트 요청 dto")
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
        @ApiModel(description = "거래글 등록 dto")
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
            
            @ApiModelProperty(value = "거래글 파일")
            private List<MultipartFile> files;
        }

        @Data
        @ApiModel(description = "공지사항 등록 dto")
        public static class NoticeInsert {
            @NotEmpty(message = "제목은 필수입니다.")
            @ApiModelProperty(value = "공지사항 게시글 제목", required = true)
            private String title;
        }
    }

    public static class Response {

        @Getter
        @Builder
        @ApiModel(description = "거래글 리스트 dto")
        public static class TradeList {
            @ApiModelProperty(value = "게시글 ID")
            private Long id;

            @ApiModelProperty(value = "게시글 제목")
            private String title;

            @ApiModelProperty(value = "모델명")
            private String modelName;

            @ApiModelProperty(value = "업체명")
            private String company;

            @ApiModelProperty(value = "주행거리")
            private int mileage;

            @ApiModelProperty(value = "CC")
            private int cc;

            @ApiModelProperty(value = "연식")
            private int year;

            @ApiModelProperty(value = "가격")
            private int price;
        }

        @Getter
        @Builder
        @ApiModel(description = "거래글 상세 dto")
        public static class TradeContent {
            @ApiModelProperty(value = "게시글 ID")
            private Long boardId;

            @ApiModelProperty(value = "게시글 제목")
            private String title;

            @ApiModelProperty(value = "모델명")
            private String modelName;

            @ApiModelProperty(value = "업체명")
            private String company;

            @ApiModelProperty(value = "주행거리")
            private int mileage;

            @ApiModelProperty(value = "CC")
            private int cc;

            @ApiModelProperty(value = "연식")
            private int year;

            @ApiModelProperty(value = "가격")
            private int price;

            @ApiModelProperty(value = "거래지역명")
            private String location;

            @ApiModelProperty(value = "구매 일자")
            private String dateOfPurchase;

            @ApiModelProperty(value = "내 게시글 여부")
            private Boolean isMyPost;

            private PageDto<CommentDto.Response.Info> comments;
        }

        @Getter
        @Builder
        @ApiModel(description = "공지사항 리스트 dto")
        public static class NoticeList {
            @ApiModelProperty(value = "게시글 ID")
            private Long id;

            @ApiModelProperty(value = "게시글 제목")
            private String title;

            @ApiModelProperty(value = "작성 일자")
            private String date;
        }

        @Getter
        @Builder
        @ApiModel(description = "공지사항 상세 dto")
        public static class NoticeContent {
            @ApiModelProperty(value = "게시글 제목")
            private String title;
        }

    }
}
