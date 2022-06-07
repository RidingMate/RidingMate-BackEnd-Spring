package com.ridingmate.api.consts;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {
    
    // 성공
    SUCCESS(HttpStatus.OK, 9999, "success"),

    // 기본 에러
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, 1000, "파라미터 값이 잘못되었습니다"),
    METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, 1001, "파라미터 타입이 잘못되었습니다"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED, 1002, "이 요청은 해당 메소드를 지원하지 않습니다."),

    // 인증 관련 에러
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, 2000, "유저를 찾지 못하였습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 2001, "잘못된 토큰입니다"),

    // 데이터 관련
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, 3000, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_MODEL(HttpStatus.NOT_FOUND,3001, "제조사를 찾을 수 없습니다."),
    NOT_FOUND_YEAR(HttpStatus.NOT_FOUND, 3002, "연식을 찾을 수 없습니다."),
    NOT_FOUND_BIKE(HttpStatus.NOT_FOUND,3003, "바이크를 찾을 수 없습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND,3004, "게시글을 찾을 수 없습니다."),
    MILEAGE_INPUT_ERROR(HttpStatus.BAD_REQUEST, 3005, "현재 주행거리보다 입력 주행거리가 짧습니다."),
    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND,3006, "지역코드를 찾을 수 없습니다."),

    NOT_WRITER_OF_BOARD(HttpStatus.BAD_REQUEST, 3007, "게시글의 작성자가 아닙니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 3999, "파라미터 값이 잘못되었습니다")
    ;

    @JsonIgnore
    private HttpStatus status;

    @JsonProperty(value = "code")
    @ApiModelProperty(value = "응답 코드")
    private int responseCode;

    @JsonProperty(value = "message")
    @ApiModelProperty(value = "응답 메시지")
    private String responseMessage;
}
