package com.ridingmate.api.payload.common;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@ApiModel(description = "파라미터 에러 상세 내용")
@RequiredArgsConstructor
public class BindingErrorDto {

    private final String field;
    private final String message;
}
