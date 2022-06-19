package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class BikeInsertRequest {

    @ApiModelProperty(value = "제조사 명", required = true)
    private String company;

    @ApiModelProperty(value = "모델 명", required = true)
    private String model;

    @ApiModelProperty(value = "연식", required = true)
    private int year;

    @ApiModelProperty(value = "대표 바이크 체크 - representative(대표 바이크), normal(대표 바이크 x)", required = true)
    private String bikeRole;

    @ApiModelProperty(value = "바이크 닉네임", required = true)
    private String bikeNickName;

    @ApiModelProperty(value = "현재 주행거리", required = true)
    private int mileage;

    @ApiModelProperty(value = "구입 날짜", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfPurchase;
}
