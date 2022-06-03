package com.ridingmate.api.payload.user.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import com.ridingmate.api.payload.user.dto.FuelDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FuelListResponse {
    List<FuelDto> fuelList;
    @ApiModelProperty(value = "최근 연비")
    private double fuelEfficiency;

    public FuelListResponse convertEntityToResponse(List<FuelEntity> fuelEntities, BikeEntity bikeEntity){
        this.fuelList = fuelEntities.stream().map(fuelEntity -> new FuelDto().convertEntityToDto(fuelEntity)).collect(Collectors.toList());
        this.fuelEfficiency = bikeEntity.getFuelEfficiency();

        return this;
    }
}
