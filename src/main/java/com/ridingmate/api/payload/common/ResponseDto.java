package com.ridingmate.api.payload.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ridingmate.api.consts.ResponseCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@JsonPropertyOrder({
        "status",
        "code",
        "message",
        "response"
})
public class ResponseDto<T> {

    @JsonProperty("response")
    @ApiModelProperty("데이터")
    private T response;

    @JsonProperty("status")
    @ApiModelProperty("상태")
    private Status status;

    @JsonIgnore
    @Builder.Default
    private ResponseCode responseCode = ResponseCode.SUCCESS;

    @JsonProperty("status")
    public Status status() {
        return Status.builder()
                     .code(responseCode.getResponseCode())
                     .message(responseCode.getResponseMessage())
                     .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @ApiModel(description = "응답 상태")
    public static class Status {
        @ApiModelProperty(value = "응답 코드")
        private int code;
        @ApiModelProperty(value = "응답 메시지")
        private String message;
    }

}
