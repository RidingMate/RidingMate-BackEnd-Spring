package com.ridingmate.api.payload.user.request;

import com.ridingmate.api.entity.BikeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceUpdateRequest extends MaintenanceInsertRequest {

    @ApiModelProperty(value = "정비 idx", required = true)
    private Long Idx;

}
