package com.ridingmate.api.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.payload.common.ResponseDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        String result = objectMapper.writeValueAsString(ResponseDto.builder()
                                                                   .response(exception.getMessage())
                                                                   .responseCode(ResponseCode.FAIL_SOCIAL_LOGIN)
                                                                   .build());
        writer.print(result);
        getRedirectStrategy().sendRedirect(request, response, "/");
    }
}
