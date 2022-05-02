package com.ridingmate.api.payload;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BikeInsertRequest {

    @NotEmpty(message = "제조사는 필수입니다.")
    private String company;

    @NotEmpty(message = "모델명은 필수 입니다.")
    private String model;

    @NotEmpty(message = "연식은 필수입니다.")
    private int year;

    private String bikeNickName;

    private int mileage;
}
