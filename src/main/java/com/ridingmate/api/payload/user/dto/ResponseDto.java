package com.ridingmate.api.payload.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ridingmate.api.consts.ResponseCode;

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
    private T response;

    @JsonProperty("status")
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
    public static class Status {
        private int code;
        private String message;
    }

}
