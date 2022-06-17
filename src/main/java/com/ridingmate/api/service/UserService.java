package com.ridingmate.api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.SocialUserEntity;
import com.ridingmate.api.entity.value.UserRole;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.AuthResponse;
import com.ridingmate.api.payload.user.dto.NormalUserDto;
import com.ridingmate.api.repository.UserRepository;
import com.ridingmate.api.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse normalJoin(NormalUserDto.Request.Join request) {
        if (userRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new CustomException(ResponseCode.DUPLICATE_USER);
        }

        NormalUserEntity normalUser = new NormalUserEntity(
                request.getUserId(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                UserRole.ROLE_USER);
        userRepository.save(normalUser);

        return new AuthResponse(getNormalUserToken(normalUser.getUserId()));
    }

    @Transactional
    public AuthResponse socialJoin() {

        // TODO : 중복 체크

        // TODO : DB에 회원정보 등록
        SocialUserEntity socialUser = SocialUserEntity.builder().build();
        userRepository.save(socialUser);

        // TODO : 토큰 발급
        String token = null;

        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse normalLogin(NormalUserDto.Request.Login request) {
        NormalUserEntity normalUser = userRepository.findByUserId(request.getUserId()).orElseThrow(()
                -> new CustomException(ResponseCode.NOT_FOUND_USER));
        if (!passwordEncoder.matches(request.getPassword(), normalUser.getPassword())) {
            throw new CustomException(ResponseCode.NOT_MATCH_USER_INFO);
        }
        return new AuthResponse(getNormalUserToken(normalUser.getUserId()));
    }

    @Transactional
    public AuthResponse socialLogin() {
        // TODO : 소셜유저 조회

        String token = null;
        return new AuthResponse(token);
    }

    private String getNormalUserToken(String userId) {
        return jwtTokenProvider.generateToken(userId);
    }
}
