package com.ridingmate.api.security;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.JwtDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    private String secretKey = "SECRET_KEY";

    private static final long VALIDITY_IN_MILLISECONDS = 1000L * 60 * 60 * 24 * 90; //90일

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 토큰 생성
     * @param subject 유저 구분자(ex. 유저 아이디)
     * @param userIdx 유저 번호
     * @param isSocialUser 소셜 유저 여부
     * @return token
     */
    public String generateToken(String subject, Long userIdx, Boolean isSocialUser) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plus(VALIDITY_IN_MILLISECONDS, ChronoUnit.MILLIS);
        return Jwts.builder()
                   .claim("userIdx", userIdx)
                   .claim("isSocialUser", isSocialUser)
                   .setSubject(subject)
                   .setIssuedAt(Timestamp.valueOf(now))
                   .setExpiration(Timestamp.valueOf(validity))
                   .signWith(SignatureAlgorithm.HS512, secretKey)
                   .compact();
    }

    /**
     * Header 토큰값 분리
     * @param request 요청 객체
     * @return token
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        if(bearerToken != null && !bearerToken.startsWith("Bearer ")) {
            return "";
        }

        return null;
    }

    /**
     * Jwt 토큰 검증, 파싱
     * @param token 파싱, 검증할 토큰
     * @return 파싱된 정보가 담긴 JwtDto
     */
    public JwtDto parseToken(String token) {
        if (StringUtils.hasText(token)) {
            try {
                return JwtDto.of(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody());
            } catch (Exception e) {
                throw new CustomException(ResponseCode.INVALID_TOKEN);
            }
        }
        return null;
    }

}
