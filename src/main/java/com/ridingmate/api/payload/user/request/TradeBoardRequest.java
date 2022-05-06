package com.ridingmate.api.payload.user.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class TradeBoardRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    private String price;

    private String company;

    private String modelName;

    private double fuelEfficiency;

    private String cc;

    private String year;

    private String mileage;
}
