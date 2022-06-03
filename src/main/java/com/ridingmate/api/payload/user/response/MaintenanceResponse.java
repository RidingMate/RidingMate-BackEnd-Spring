package com.ridingmate.api.payload.user.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MaintenanceResponse {

    private Long idx;
    private String title;
    private String area;
    private LocalDate dateOfMaintenance;
    private String location;
    private String amount;
    private String content;
    private BikeEntity bikeEntity;

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
                .bikeEntity(maintenanceEntity.getBike())
                .build();
    }

}
