package com.ridingmate.api.payload.user.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.entity.FuelEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FuelDto {
    @ApiModelProperty(value = "주유량")
    private int fuelVolume;
    @ApiModelProperty(value = "주유 가격")
    private int fuelAmount;

    public FuelDto convertEntityToDto(FuelEntity fuelEntity){
        this.fuelVolume = fuelEntity.getFuelVolume();
        this.fuelAmount = fuelEntity.getFuelAmount();
        return this;
    }
}
