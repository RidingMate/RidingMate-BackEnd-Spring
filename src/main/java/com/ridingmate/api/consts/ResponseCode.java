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
    SUCCESS_PAYMENT(9999, "결제를 성공하였습니다."),
    SUCCESS_ISSUE_V_BANK(9998, "가상계좌가 발급되었습니다."),
    
    // 에러
    // 로직 에러(무조건 내부에러)
    DIVIDE_ZERO(1000, "0으로 나눈 부분이 있어요"),
    ARRAY_OUT_OF_RANGE(1001, "배열 인덱스가 배열에 없어요"),
    DONT_PARSE_NUMBER(1002, "해당 문자열은 숫자로 바꿀 수 없어요"),
    DONT_CAST_CLASS(1003, "해당 클래스로 변환할 수 없어요"),
    INDEX_OUT_OF_BOUND(1004, "리스트에 해당 인덱스가 없어요"),

    // 인증 관련 에러
    NOT_FOUND_USER(2000, "유저를 찾지 못하였습니다."),


    NOT_FOUND_COMPANY(3000, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_MODEL(3001, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_YEAR(3002, "연식을 찾을 수 없습니다."),
    NOT_FOUND_BIKE(3003, "바이크를 찾을 수 없습니다."),
    ;



    @JsonProperty(value = "code")
    private int responseCode;

    @JsonProperty(value = "message")
    private String responseMessage;
}
