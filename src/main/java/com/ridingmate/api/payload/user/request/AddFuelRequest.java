package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AddFuelRequest {

    @ApiModelProperty(value = "바이크 idx")
    private long bike_idx;

    @ApiModelProperty(value = "주유 날짜")
    private LocalDate time;

    @ApiModelProperty(value = "현재 주행 거리")
    private int mileage;

    @ApiModelProperty(value = "주유 가격")
    private int amount;
}
