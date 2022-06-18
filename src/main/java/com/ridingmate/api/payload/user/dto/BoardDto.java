package com.ridingmate.api.payload.user.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.ridingmate.api.entity.TradeBoardEntity;

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
            @ApiModelProperty("제조사")
            private String company;

            @ApiModelProperty("모델명")
            private String modelName;

            @ApiModelProperty("지역코드")
            private String locationCode;

            @ApiModelProperty("주행거리 최소값")
            private int minMileage;

            @ApiModelProperty("주행거리 최대값")
            private int maxMileage = 999999999;

            @ApiModelProperty("가격 최소값")
            private int minPrice;

            @ApiModelProperty("가격 최대값")
            private int maxPrice = 999999999;

            @ApiModelProperty("연식 최소값")
            private int minYear;

            @ApiModelProperty("연식 최대값")
            private int maxYear = 999999999;

            @ApiModelProperty("배기량 최소값")
            private int minCc;

            @ApiModelProperty("배기량 최대값")
            private int maxCc = 999999999;

            @ApiModelProperty("거래 완료 안보기 체크 Y / N")
            @Pattern(regexp = "^[Y|N]{1}$", message = "Y와 N만 입력 가능합니다.")
            private String hideComplete = "N";
        }

        @Data
        @ApiModel(description = "거래글 등록 dto")
        public static class TradeInsert {
            @NotEmpty(message = "제목은 필수입니다.")
            @ApiModelProperty(value = "거래글 제목", required = true)
            private String title;

            @ApiModelProperty("가격")
            @Min(value = 0, message = "가격은 0원 이상만 가능합니다.")
            private int price;

            @ApiModelProperty("업체명")
            private String company;

            @ApiModelProperty("모델명")
            private String modelName;

            @ApiModelProperty("연비")
            private double fuelEfficiency;

            @ApiModelProperty("cc")
            private int cc;

            @ApiModelProperty("연식")
            private int year;

            @ApiModelProperty("주행거리")
            @Min(value = 0, message = "주행거리는 0이상만 가능합니다.")
            private int mileage;

            @ApiModelProperty("지역 코드")
            private String locationCode;

            @ApiModelProperty("구매 일자 연도")
            private Integer purchaseYear;

            @ApiModelProperty("구매 일자 월")
            private Integer purchaseMonth;

            @ApiModelProperty("상세 내용")
            private String content;

            @ApiModelProperty("연락처")
            private String phoneNumber;

            @ApiModelProperty("구매자에게 주유/정비 정보 공개하기 Y/N")
            @Pattern(regexp = "^[Y|N]{1}$", message = "Y와 N만 입력 가능합니다.")
            private String isOpenToBuyer;
            
            @ApiModelProperty("거래글 파일")
            private List<MultipartFile> files;

            @ApiModelProperty("내 바이크 Idx")
            private Long bikeIdx;
        }

        @Data
        @ApiModel(description = "거래글 수정 dto")
        public static class TradeUpdate {
            @NotEmpty(message = "거래글 id는 필수입니다.")
            @ApiModelProperty(value = "거래글 ID", required = true)
            private Long boardId;

            @NotEmpty(message = "제목은 필수입니다.")
            @ApiModelProperty(value = "거래글 제목", required = true)
            private String title;

            @ApiModelProperty("가격")
            @Min(0)
            private int price;

            @ApiModelProperty("업체명")
            private String company;

            @ApiModelProperty("모델명")
            private String modelName;

            @ApiModelProperty("연비")
            private double fuelEfficiency;

            @ApiModelProperty("cc")
            private int cc;

            @ApiModelProperty("연식")
            private int year;

            @ApiModelProperty("주행거리")
            @Min(0)
            private int mileage;

            @ApiModelProperty("지역 코드")
            private String locationCode;

            @ApiModelProperty("구매 일자 연도")
            private Integer purchaseYear;

            @ApiModelProperty("구매 일자 월")
            private Integer purchaseMonth;

            @ApiModelProperty("상세 내용")
            private String content;

            @ApiModelProperty("연락처")
            private String phoneNumber;

            @ApiModelProperty("구매자에게 주유/정비 정보 공개하기 Y/N")
            @Pattern(regexp = "^[Y|N]{1}$", message = "Y와 N만 입력 가능합니다.")
            private String isOpenToBuyer;

            @ApiModelProperty("거래글 파일")
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
            @ApiModelProperty("게시글 ID")
            private Long id;

            @ApiModelProperty("게시글 제목")
            private String title;

            @ApiModelProperty("모델명")
            private String modelName;

            @ApiModelProperty("업체명")
            private String company;

            @ApiModelProperty("주행거리")
            private int mileage;

            @ApiModelProperty("CC")
            private int cc;

            @ApiModelProperty("연식")
            private int year;

            @ApiModelProperty("가격")
            private int price;

            public static TradeList of(TradeBoardEntity tradeBoard) {
                return builder()
                        .id(tradeBoard.getIdx())
                        .title(tradeBoard.getTitle())
                        .company(tradeBoard.getCompany())
                        .modelName(tradeBoard.getModelName())
                        .price(tradeBoard.getPrice())
                        .cc(tradeBoard.getCc())
                        .mileage(tradeBoard.getMileage())
                        .year(tradeBoard.getYear())
                        .build();
            }
        }

        @Getter
        @Builder
        @ApiModel(description = "거래글 상세 dto")
        public static class TradeInfo {
            @ApiModelProperty("게시글 ID")
            private Long boardId;

            @ApiModelProperty("게시글 제목")
            private String title;

            @ApiModelProperty("모델명")
            private String modelName;

            @ApiModelProperty("업체명")
            private String company;

            @ApiModelProperty("주행거리")
            private int mileage;

            @ApiModelProperty("CC")
            private int cc;

            @ApiModelProperty("연식")
            private int year;

            @ApiModelProperty("가격")
            private int price;

            @ApiModelProperty("거래지역명")
            private String location;

            @ApiModelProperty("구매 일자")
            private String dateOfPurchase;

            @ApiModelProperty("내 게시글 여부")
            private Boolean isMyPost;

            private PageDto<CommentDto.Response.Info> comments;

            public static TradeInfo of(TradeBoardEntity board, Page<CommentDto.Response.Info> comments, Long userIdx) {
                return builder().boardId(board.getIdx())
                                .title(board.getTitle())
                                .company(board.getCompany())
                                .modelName(board.getModelName())
                                .price(board.getPrice())
                                .cc(board.getCc())
                                .mileage(board.getMileage())
                                .year(board.getYear())
                                .dateOfPurchase(board.getDateOfPurchase() != null ? board.getDateOfPurchase().toString() : null)
                                .location(board.getLocation() != null ?
                                          board.getLocation().getName() : "")
                                .comments(new PageDto<>(comments))
                                .isMyPost(board.getUser() != null && Objects.equals(board.getUser().getIdx(),
                                                                                    userIdx))
                                .build();
            }
        }

        @Getter
        @Builder
        @ApiModel(description = "공지사항 리스트 dto")
        public static class NoticeList {
            @ApiModelProperty("게시글 ID")
            private Long id;

            @ApiModelProperty("게시글 제목")
            private String title;

            @ApiModelProperty("작성 일자")
            private String date;
        }

        @Getter
        @Builder
        @ApiModel(description = "공지사항 상세 dto")
        public static class NoticeInfo {
            @ApiModelProperty("게시글 제목")
            private String title;
        }

    }
}
