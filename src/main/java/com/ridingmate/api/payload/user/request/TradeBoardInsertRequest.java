package com.ridingmate.api.payload.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TradeBoardInsertRequest {

    @ApiModelProperty(value = "거래글 제목", required = true)
    private String title;

    @ApiModelProperty(value = "거래 가격", required = true)
    private String price;
}
