package com.ridingmate.api.payload.user.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(description = "주유 요청 dto")
public class FuelDto {

    @ApiModel(description = "주유 요청 dto")
    public static class Request{

        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
        public static class FuelInfo{
            @ApiModelProperty(value = "주유량")
            private int fuelVolume;
            @ApiModelProperty(value = "주유 가격")
            private int fuelAmount;

            public static FuelInfo convertEntityToDto(FuelEntity fuelEntity){
                return FuelInfo.builder()
                        .fuelAmount(fuelEntity.getFuelAmount())
                        .fuelVolume(fuelEntity.getFuelVolume())
                        .build();
            }
        }

        @Getter
        @ApiModel(description = "주유 기록 추가 요청")
        public static class AddFuel{
            @ApiModelProperty(value = "바이크 idx")
            private long bike_idx;

            @ApiModelProperty(value = "주유 날짜")
            private LocalDate date;

            @ApiModelProperty(value = "현재 주행 거리")
            private int mileage;

            @ApiModelProperty(value = "주유량")
            private int fuelVolume;

            @ApiModelProperty(value = "주유 가격")
            private int amount;
        }

    }

    @ApiModel(description = "주유 응답 dto")
    public static class Response{

        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
        public static class FuelList{
            List<Request.FuelInfo> fuelList;
            @ApiModelProperty(value = "최근 연비")
            private double fuelEfficiency;

            public static FuelList convertEntityToResponse(List<FuelEntity> fuelEntities, BikeEntity bikeEntity){
                return FuelList.builder()
                        .fuelList(fuelEntities.stream().map(fuelEntity -> Request.FuelInfo.convertEntityToDto(fuelEntity)).collect(Collectors.toList()))
                        .fuelEfficiency(bikeEntity.getFuelEfficiency())
                        .build();
            }
        }

    }


}
