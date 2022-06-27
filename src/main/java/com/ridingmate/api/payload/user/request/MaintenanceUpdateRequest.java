package com.ridingmate.api.payload.user.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MaintenanceUpdateRequest{

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

    @ApiModelProperty(value="삭제된 파일의 이름 리스트")
    private List<String> deletedFileNameList = new ArrayList<>();

}
