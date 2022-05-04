package com.ridingmate.api.payload.user.request;

import com.ridingmate.api.payload.user.request.BikeInsertRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class BikeUpdateRequest extends BikeInsertRequest {

    @ApiModelProperty(value = "바이크 idx값", required = true)
    private long idx;

}
