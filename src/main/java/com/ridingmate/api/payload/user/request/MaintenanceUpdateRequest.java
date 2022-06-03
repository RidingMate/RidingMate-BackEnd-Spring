package com.ridingmate.api.payload.user.request;

import com.ridingmate.api.entity.BikeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MaintenanceUpdateRequest extends MaintenanceInsertRequest {

    @ApiModelProperty(value = "정비 idx", required = true)
    private Long idx;

    // 바이크 정보 필요??
    @ApiModelProperty(value = "bike", required = true)
    private BikeEntity bike;
}
