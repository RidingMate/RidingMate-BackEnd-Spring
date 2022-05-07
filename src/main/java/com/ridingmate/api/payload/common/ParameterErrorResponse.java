package com.ridingmate.api.payload.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class ParameterErrorResponse {

    private CodeMessage response;

    public ParameterErrorResponse(int code, String message) {
        response = new CodeMessage();
        response.setCode(code);
        response.setMessage(message);
    }

    @Setter
    @Getter
    private class CodeMessage {
        private int code;
        private String message;
    }
}
