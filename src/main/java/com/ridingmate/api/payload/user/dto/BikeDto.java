package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.BikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BikeDto {
    private Long idx;
    //제조사
    private String company;
    //모델명
    private String model;
    //년식
    private int year;
    //주행거리
    private int mileage;
    //연비
    private int fuelEfficiency;
    //주유 횟수
    private int countOiling;
    //정비 횟수
    private int countMaintenance;
    //바이크 별명
    private String bikeNickname;

    public static BikeDto convertEntityToDto(BikeEntity bike) {
        return new BikeDto(
                bike.getIdx(),
                bike.getCompany(),
                bike.getModel(),
                bike.getYear(),
                bike.getMileage(),
                bike.getFuelEfficiency(),
                bike.getCountOiling(),
                bike.getCountMaintenance(),
                bike.getBikeNickname()
        );
    }

}
