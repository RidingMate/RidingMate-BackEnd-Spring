package com.ridingmate.api.consts;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {
    
    // 성공
    SUCCESS(HttpStatus.OK, 9999, "success"),

    // 인증 관련 에러
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, 2000, "유저를 찾지 못하였습니다."),

    // 데이터 관련
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, 3000, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_MODEL(HttpStatus.NOT_FOUND,3001, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_YEAR(HttpStatus.NOT_FOUND, 3002, "연식을 찾을 수 없습니다."),
    NOT_FOUND_BIKE(HttpStatus.NOT_FOUND,3003, "바이크를 찾을 수 없습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND,3004, "게시글을 찾을 수 없습니다."),
    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND,3005, "지역코드를 찾을 수 없습니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 3999, "파라미터 값이 잘못되었습니다")
    ;

    private HttpStatus status;

    @JsonProperty(value = "code")
    private int responseCode;

    @JsonProperty(value = "message")
    private String responseMessage;
}
