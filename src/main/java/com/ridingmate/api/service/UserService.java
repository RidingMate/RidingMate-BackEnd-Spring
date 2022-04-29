package com.ridingmate.api.service;

import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.SocialUserEntity;
import com.ridingmate.api.payload.AuthResponse;
import com.ridingmate.api.payload.NormalJoinRequest;
import com.ridingmate.api.repository.UserRepository;
import com.ridingmate.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    // 일반 회원가입
    @Transactional
    public AuthResponse normalJoin() {

        // TODO : 중복 체크

        // TODO : DB에 회원정보 등록
        NormalUserEntity normalUser = NormalUserEntity.builder().build();
        userRepository.save(normalUser);

        // TODO : 토큰 발급

        return new AuthResponse("success");
    }

    // 소셜 회원가입
    @Transactional
    public AuthResponse socialJoin() {

        // TODO : 중복 체크

        // TODO : DB에 회원정보 등록
        SocialUserEntity socialUser = SocialUserEntity.builder().build();
        userRepository.save(socialUser);

        // TODO : 토큰 발급

        return new AuthResponse("success");
    }

    // 일반 로그인
    @Transactional
    public AuthResponse normalLogin(NormalJoinRequest request) {
        // TODO : 일반유저 조회
        NormalUserEntity normalUser = userRepository.findByUserId(request.getUserId()).orElseThrow(() -> new NullPointerException("유저를 찾지 못하였습니다."));

        // TODO : 토큰 반환
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(normalUser.getUserId(), normalUser.getPassword());

        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = jwtTokenProvider.generateToken(authenticate);

        return new AuthResponse(jwtToken, "success");
    }

    // 소셜 로그인
    @Transactional
    public AuthResponse socialLogin() {
        // TODO : 소셜유저 조회

        return new AuthResponse("success");
    }
}
