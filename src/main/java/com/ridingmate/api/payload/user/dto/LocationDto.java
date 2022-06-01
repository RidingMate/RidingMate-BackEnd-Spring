package com.ridingmate.api.payload.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "지역 리스트 조회 값")
public class LocationDto {

    @ApiModelProperty(value = "지역 코드")
    private String code;

    @ApiModelProperty(value = "지역명")
    private String name;
}
