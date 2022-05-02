package com.ridingmate.api.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BikeUpdateRequest extends BikeInsertRequest{

    @ApiModelProperty(value = "바이크 idx값", required = true)
    private long idx;

}
