package com.ridingmate.api.service;

import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.SocialUserEntity;
import com.ridingmate.api.repository.UserRepository;
import com.ridingmate.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 일반 회원가입
    public void normalJoin() {

        // TODO : 중복 체크

        // TODO : DB에 회원정보 등록
        NormalUserEntity normalUser = NormalUserEntity.builder().build();
        userRepository.save(normalUser);

        // TODO : 토큰 발급
    }

    // 소셜 회원가입
    public void socialJoin() {

        // TODO : 중복 체크

        // TODO : DB에 회원정보 등록
        SocialUserEntity socialUser = SocialUserEntity.builder().build();
        userRepository.save(socialUser);

        // TODO : 토큰 발급
    }

    // 일반 로그인
    public void normalLogin() {
        // TODO : 일반유저 조회
    }

    // 소셜 로그인
    public void socialLogin() {
        // TODO : 소셜유저 조회
    }
}
