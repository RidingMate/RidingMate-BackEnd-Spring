package com.ridingmate.api.payload.user.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.value.BikeRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ApiModel(description = "바이크 관련 dto")
public class BikeDto {

    @ApiModel(description = "바이크 요청 dto")
    public static class Request{

        @Builder
        @Data
        @AllArgsConstructor
        @ApiModel(description = "바이크 정보 요청 dto")
        public static class BikeInfo{
            @ApiModelProperty("idx")
            private Long idx;

            @ApiModelProperty("제조사")
            private String company;

            @ApiModelProperty("모델명")
            private String model;

            @ApiModelProperty("년식")
            private int year;

            @ApiModelProperty("주행거리")
            private int mileage;

            @ApiModelProperty("얀비")
            private double fuelEfficiency;

            @ApiModelProperty("주유 횟수")
            private int countOiling;

            @ApiModelProperty("정비 횟수")
            private int countMaintenance;

            @ApiModelProperty("바이크 별명")
            private String bikeNickname;

            public static BikeInfo convertEntityToDto(BikeEntity bike) {
                return new BikeInfo(
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

        @Getter
        @ApiModel(description = "바이크 추가 요청")
        public static class AddBike{
            @ApiModelProperty(value = "제조사 명", required = true)
            private String company;

            @ApiModelProperty(value = "모델 명", required = true)
            private String model;

            @ApiModelProperty(value = "연식", required = true)
            private int year;
        }


        @Getter
        @AllArgsConstructor
        @ApiModel(description = "바이크 등록 요청")
        public static class BikeInsert{
            @ApiModelProperty(value = "제조사 명", required = true)
            private String company;

            @ApiModelProperty(value = "모델 명", required = true)
            private String model;

            @ApiModelProperty(value = "연식", required = true)
            private int year;

            @ApiModelProperty(value = "대표 바이크 체크 - representative(대표 바이크), normal(대표 바이크 x)", required = true)
            private String bikeRole;

            @ApiModelProperty(value = "바이크 닉네임", required = true)
            private String bikeNickName;

            @ApiModelProperty(value = "현재 주행거리", required = true)
            private int mileage;

            @ApiModelProperty(value = "구입 날짜", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            private LocalDate dateOfPurchase;
        }

        @Getter
        @AllArgsConstructor
        @ApiModel(description = "바이크 수정 요청")
        public static class BikeUpdate{
            @ApiModelProperty(value = "바이크 idx값", required = true)
            private long idx;

            @ApiModelProperty(value = "제조사 명", required = true)
            private String company;

            @ApiModelProperty(value = "모델 명", required = true)
            private String model;

            @ApiModelProperty(value = "연식", required = true)
            private int year;

            @ApiModelProperty(value = "대표 바이크 체크 - representative(대표 바이크), normal(대표 바이크 x)", required = true)
            private String bikeRole;

            @ApiModelProperty(value = "바이크 닉네임")
            private String bikeNickName;

            @ApiModelProperty(value = "현재 주행거리")
            private int mileage;

            @ApiModelProperty(value = "구입 날짜")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            private LocalDate dateOfPurchase;
        }

        @Builder
        @Data
        @ApiModel(description = "바이크 검색 요청")
        @AllArgsConstructor
        public static class BikeSearch{
            @ApiModelProperty(value = "검색 내용", required = true)
            private String content;
        }

    }

    @ApiModel(description = "바이크 응답 dto")
    public static class Response{

        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
        public static class MyBike{
            @ApiModelProperty(value = "idx")
            private long idx;
            @ApiModelProperty(value = "누적 주행거리")
            private int milage;
            @ApiModelProperty(value = "평균 연비")
            private double fuelEfficiency;
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
            @ApiModelProperty(value = "image")
            private String image;

            public static MyBike convertEntityToResponse(BikeEntity bikeEntity){
                String image = "";
                if(bikeEntity.getFileEntity() != null){
                    image = bikeEntity.getFileEntity().getLocation();
                }
                return MyBike.builder()
                        .idx(bikeEntity.getIdx())
                        .milage(bikeEntity.getMileage())
                        .fuelEfficiency(bikeEntity.getFuelEfficiency())
                        .dateOfPurchase(bikeEntity.getDateOfPurchase())
                        .countOiling(bikeEntity.getCountOiling())
                        .countMaintenance(bikeEntity.getCountMaintenance())
                        .bikeNickname(bikeEntity.getBikeNickname())
                        .bikeRole(bikeEntity.getBikeRole())
                        .image(image)
                        .build();
            }
        }

    }


}
