package com.ridingmate.api.payload.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FileEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Data
public class MaintenanceInsertRequest {

    @ApiModelProperty(value = "바이크 idx", required = true)
    private Long bike_idx;

    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @ApiModelProperty(value = "서비스센터/기관명", required = true)
    private String area;

    @ApiModelProperty(value = "정비 날짜", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfMaintenance;

    @ApiModelProperty(value = "정비 위치", required = true)
    private String location;

    @ApiModelProperty(value = "가격", required = true)
    private int amount;

    // TODO : 사진 기록해야함 -> 멀티파트를 이용한 썸네일 저장

    @ApiModelProperty(value = "제목")
    private String content;
}
