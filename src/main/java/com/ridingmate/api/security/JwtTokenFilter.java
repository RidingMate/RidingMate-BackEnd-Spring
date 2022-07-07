package com.ridingmate.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.payload.user.dto.JwtDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        try {
            //토큰 발급 요청시에는 필터 통과 처리
            if (!request.getRequestURI().startsWith("/token") ||
                !request.getRequestURI().startsWith("/normal/join") ||
                !request.getRequestURI().startsWith("/social/join") ||
                !request.getRequestURI().startsWith("/normal/login") ||
                !request.getRequestURI().startsWith("/social/login")
            ) {
                JwtDto jwtDto = jwtTokenProvider.parseToken(token);
                if (jwtDto != null) {
                    Authentication auth = getAuthentication(jwtDto);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            errorResponse(response, e.getErrorCode(), null);
        } catch (Exception e) {
            errorResponse(response, ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private Authentication getAuthentication(JwtDto dto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void errorResponse(HttpServletResponse response, ResponseCode responseCode, Object message) throws IOException {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(responseCode.getResponseCode());
            response.getWriter()
                    .write(objectMapper.writeValueAsString(ResponseDto.builder()
                                                                      .response(message)
                                                                      .responseCode(responseCode)
                                                                      .build()));
        } catch (IOException e) {
            response.sendError(responseCode.getResponseCode(), responseCode.getResponseMessage());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
