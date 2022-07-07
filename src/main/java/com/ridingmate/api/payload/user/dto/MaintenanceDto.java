package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(description = "정비 dto")
public class MaintenanceDto {

    @ApiModel(description = "정비 요청 dto")
    public static class Request {

        @Data
        @ApiModel(description = "정비 등록 요청 dto")
        public static class MaintenanceInsertRequest {

            @ApiModelProperty(value = "바이크 idx", required = true)
            private Long bike_idx;

            @ApiModelProperty(value = "제목", required = true)
            private String title;

            @ApiModelProperty(value = "서비스센터/기관명", required = true)
            private String area;

            @ApiModelProperty(value = "정비 날짜", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            private LocalDate dateOfMaintenance;

            @ApiModelProperty(value = "정비 위치", required = true)
            private String location;

            @ApiModelProperty(value = "가격", required = true)
            private int amount;

            @ApiModelProperty(value = "사진")
            private List<MultipartFile> files = new ArrayList<>();

            @ApiModelProperty(value = "내용")
            private String content;
        }

        @Data
        @ApiModel(description = "정비 수정 요청 dto")
        public static class MaintenanceUpdateRequest {

            @ApiModelProperty(value = "바이크 idx", required = true)
            private Long bike_idx;

            @ApiModelProperty(value = "제목", required = true)
            private String title;

            @ApiModelProperty(value = "서비스센터/기관명", required = true)
            private String area;

            @ApiModelProperty(value = "정비 날짜", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            private LocalDate dateOfMaintenance;

            @ApiModelProperty(value = "정비 위치", required = true)
            private String location;

            @ApiModelProperty(value = "가격", required = true)
            private int amount;

            @ApiModelProperty(value = "사진")
            private List<MultipartFile> files = new ArrayList<>();

            @ApiModelProperty(value = "내용")
            private String content;

            @ApiModelProperty(value = "정비 idx", required = true)
            private Long Idx;

            @ApiModelProperty(value = "삭제된 파일의 이름 리스트")
            private List<String> deletedFileNameList = new ArrayList<>();

        }

    }

    @ApiModel(description = "정비 응답 dto")
    public static class Response {

        @ApiModel("정비 응답 dto")
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MaintenanceResponse {

            @ApiModelProperty(value = "정비 idx")
            private Long idx;

            @ApiModelProperty(value = "정비 제목")
            private String title;

            @ApiModelProperty(value = "정비 센터,기관명")
            private String area;

            @ApiModelProperty(value = "정비 날짜")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            private LocalDate dateOfMaintenance;

            @ApiModelProperty(value = "정비 항목")
            private String location;

            @ApiModelProperty(value = "정비 가격")
            private int amount;

            @ApiModelProperty(value = "정비 내용")
            private String content;

            private List<String> files;

            public static MaintenanceResponse convertEntityToResponse(MaintenanceEntity maintenanceEntity) {
                return MaintenanceResponse.builder()
                        .idx(maintenanceEntity.getIdx())
                        .title(maintenanceEntity.getTitle())
                        .area(maintenanceEntity.getArea())
                        .dateOfMaintenance(maintenanceEntity.getDateOfMaintenance())
                        .location(maintenanceEntity.getLocation())
                        .amount(maintenanceEntity.getAmount())
                        .content(maintenanceEntity.getContent())
                        .files(maintenanceEntity.getFileEntities().stream().map(FileEntity::getLocation).collect(Collectors.toList()))
                        .build();
            }

        }

        @ApiModel("정비 리스트 응답 dto")
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MaintenanceCalcByYearResponse {

            @ApiModelProperty(value = "연도별 정비 내용 리스트")
            private List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();

            @ApiModelProperty(value = "연도별 총 정비 횟수")
            private int countMaintenance;

            @ApiModelProperty(value = "연도별 총 정비 가격")
            private int totalAmount;

            @ApiModelProperty(value = "내 바이크 Dto")
            private BikeDto.Request.BikeInfo bikeDto;
        }

    }

}
