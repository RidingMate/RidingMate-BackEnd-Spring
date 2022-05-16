package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class TradeSearchRequest {

    @ApiModelProperty(value = "제조사")
    private String company;

    @ApiModelProperty(value = "모델명")
    private String modelName;

    @ApiModelProperty(value = "거래지역 - 시군")
    private String sigun;

    @ApiModelProperty(value = "거래지역 - 구")
    private String gu;

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
