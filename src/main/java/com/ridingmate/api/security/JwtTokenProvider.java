package com.ridingmate.api.security;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final long VALIDITY_IN_MILLISECONDS = 1000L * 60 * 60 * 24 * 90; //90일

    @Autowired
    private CustomUserDetailsService customUserDetails;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

//    public String generateToken(Authentication authentication) {
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);
//
//        return Jwts.builder()
//                   .setSubject(userPrincipal.getUsername())
//                   .setIssuedAt(now)
//                   .setExpiration(validity)
//                   .signWith(SignatureAlgorithm.HS512, secretKey)
//                   .compact();
//    }

    public String generateToken(String username, Long userIdx, Boolean isSocialUser) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plus(VALIDITY_IN_MILLISECONDS, ChronoUnit.MILLIS);
        return Jwts.builder()
                   .claim("userIdx", userIdx)
                   .claim("isSocialUser", isSocialUser)
                   .setSubject(username)
                   .setIssuedAt(Timestamp.valueOf(now))
                   .setExpiration(Timestamp.valueOf(validity))
                   .signWith(SignatureAlgorithm.HS512, secretKey)
                   .compact();
    }

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

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
