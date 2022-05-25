package com.ridingmate.api.payload.user.request;

import com.ridingmate.api.entity.BikeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MaintenanceInsertRequest {

    @ApiModelProperty(value = "바이크 idx", required = true)
    private Long bike_idx;

    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @ApiModelProperty(value = "서비스센터/기관명", required = true)
    private String area;

    @ApiModelProperty(value = "정비 날짜", required = true)
    private LocalDate dateOfMaintenance;

    @ApiModelProperty(value = "정비 위치", required = true)
    private String location;

    @ApiModelProperty(value = "가격", required = true)
    private String amount;

    // TODO : 사진 기록해야함 -> 멀티파트를 이용한 썸네일 저장
    // TODO : BLOB같은거 이용해서 내용에 사진도 들어갈 수 있게 저장

    @ApiModelProperty(value = "제목", required = false)
    private String content;
}
