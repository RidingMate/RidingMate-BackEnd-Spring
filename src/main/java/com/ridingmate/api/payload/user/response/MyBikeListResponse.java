package com.ridingmate.api.payload.user.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.value.BikeRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MyBikeListResponse {

    @ApiModelProperty(value = "누적 주행거리")
    private int milage;
    @ApiModelProperty(value = "평균 연비")
    private int fuelEfficiency;
    @ApiModelProperty(value = "구입 일자")
    private LocalDate dateOfPurchase;
    @ApiModelProperty(value = "주유기록 카운트")
    private int countOiling;
    @ApiModelProperty(value = "정비 기록 카운트")
    private int countMaintenance;
    @ApiModelProperty(value = "바이크 별칭")
    private String bikeNickname;
    @ApiModelProperty(value = "대표 바이크 유무")
    private BikeRole bikeRole;

    public MyBikeListResponse convertEntityToResponse(BikeEntity bikeEntity){
        return MyBikeListResponse.builder()
                .milage(bikeEntity.getMileage())
                .fuelEfficiency(bikeEntity.getFuelEfficiency())
                .dateOfPurchase(bikeEntity.getDateOfPurchase())
                .countOiling(bikeEntity.getCountOiling())
                .countMaintenance(bikeEntity.getCountMaintenance())
                .bikeNickname(bikeEntity.getBikeNickname())
                .bikeRole(bikeEntity.getBikeRole())
                .build();
    }
}
