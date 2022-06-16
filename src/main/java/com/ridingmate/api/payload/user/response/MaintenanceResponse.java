package com.ridingmate.api.payload.user.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import com.ridingmate.api.payload.user.dto.BikeDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MaintenanceResponse {

    @ApiModelProperty(value = "정비 idx")
    private Long idx;

    @ApiModelProperty(value = "정비 제목")
    private String title;

    @ApiModelProperty(value = "정비 센터,기관명")
    private String area;

    @ApiModelProperty(value = "정비 날짜")
    private LocalDate dateOfMaintenance;

    @ApiModelProperty(value = "정비 항목")
    private String location;

    @ApiModelProperty(value = "정비 가격")
    private int amount;

    @ApiModelProperty(value = "정비 내용")
    private String content;

    // TODO : 사진 기록해야함 -> 멀티파트를 이용한 썸네일 저장
    // TODO : BLOB같은거 이용해서 내용에 사진도 들어갈 수 있게 저장

    public MaintenanceResponse convertEntityToResponse(MaintenanceEntity maintenanceEntity){
        return MaintenanceResponse.builder()
                .idx(maintenanceEntity.getIdx())
                .title(maintenanceEntity.getTitle())
                .area(maintenanceEntity.getArea())
                .dateOfMaintenance(maintenanceEntity.getDateOfMaintenance())
                .location(maintenanceEntity.getLocation())
                .amount(maintenanceEntity.getAmount())
                .content(maintenanceEntity.getContent())
                .build();
    }

}
