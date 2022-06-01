package com.ridingmate.api.payload.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@ApiModel(description = "파라미터 에러 상세 내용")
public class BindingErrorDto {

    @ApiModelProperty(value = "파라미터명")
    private final String field;

    @ApiModelProperty(value = "해당 파라미터의 오류 상세 내용")
    private final String message;
}
