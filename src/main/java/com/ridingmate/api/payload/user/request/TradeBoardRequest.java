package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class TradeBoardRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    @ApiModelProperty(value = "거래글 제목", required = true)
    private String title;

    @ApiModelProperty(value = "가격")
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
    private char isOpenToBuyer;
    
}
