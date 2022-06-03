package com.ridingmate.api.consts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {
    
    // 성공
    SUCCESS(9999, "success"),

    // 인증 관련 에러
    NOT_FOUND_USER(2000, "유저를 찾지 못하였습니다."),

    NOT_FOUND_COMPANY(3000, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_MODEL(3001, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_YEAR(3002, "연식을 찾을 수 없습니다."),
    NOT_FOUND_BIKE(3003, "바이크를 찾을 수 없습니다."),
    NOT_FOUND_BOARD(3004, "게시글을 찾을 수 없습니다."),
    MILEAGE_INPUT_ERROR(3005, "현재 주행거리보다 입력 주행거리가 짧습니다."),
    NOT_FOUND_LOCATION(3006, "지역코드를 찾을 수 없습니다."),
    ;



    @JsonProperty(value = "code")
    private int responseCode;

    @JsonProperty(value = "message")
    private String responseMessage;
}
