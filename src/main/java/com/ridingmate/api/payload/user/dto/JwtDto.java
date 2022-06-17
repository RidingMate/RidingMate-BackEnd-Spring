package com.ridingmate.api.payload.user.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class JwtDto {


    private final String issuer;        // 토큰 발급자


    private final String subject;       // 토큰 제목(유저 아이디)


    private final LocalDateTime issueAt;      // 발행 시간
    private final LocalDateTime expireAt;     // 만료 시간

    private final Claims claims;        // 파싱된 토큰 정보

    private final Long userIdx;         // 유저 Idx

    private final boolean isSocialUser; // 소셜 유저 여부

    public static JwtDto of(Claims claims) {
        if (claims != null) {
            return builder()
                    .claims(claims)
                    .issuer(claims.getIssuer())
                    .subject(claims.getSubject())
                    .issueAt(new Timestamp(claims.getIssuedAt().getTime()).toLocalDateTime())
                    .expireAt(new Timestamp(claims.getExpiration().getTime()).toLocalDateTime())
                    .userIdx(claims.get("userIdx", Long.class))
                    .isSocialUser(claims.get("isSocialUser", Boolean.class))
                    .build();
        }
        return null;
    }
}
