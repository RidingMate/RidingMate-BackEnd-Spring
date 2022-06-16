package com.ridingmate.api.payload.user.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.payload.user.dto.BikeDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MaintenanceCalcByYearResponse {

    @ApiModelProperty(value = "연도별 정비 내용 리스트")
    private List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();

    @ApiModelProperty(value = "연도별 총 정비 횟수")
    private int countMaintenance;

    @ApiModelProperty(value = "연도별 총 정비 가격")
    private int totalAmount;

    @ApiModelProperty(value = "내 바이크 Dto")
    private BikeDto bikeDto;

}
