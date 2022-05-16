package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class AddBikeRequest {

    @ApiModelProperty(value = "제조사 명", required = true)
    private String company;

    @ApiModelProperty(value = "모델 명", required = true)
    private String model;

    @ApiModelProperty(value = "연식", required = true)
    private int year;
}
