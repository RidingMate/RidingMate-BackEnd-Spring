package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class TradeBoardRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    @ApiModelProperty(value = "거래글 제목", required = true)
    private String title;

    private String price;

    private String company;

    private String modelName;

    private double fuelEfficiency;

    private String cc;

    private int year;

    private String mileage;
}
