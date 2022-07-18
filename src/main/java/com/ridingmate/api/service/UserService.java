package com.ridingmate.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.entity.value.UserRole;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.AuthResponse;
import com.ridingmate.api.payload.user.dto.NormalUserDto;
import com.ridingmate.api.payload.user.dto.UserDto;
import com.ridingmate.api.payload.user.dto.UserDto.Response.Count;
import com.ridingmate.api.payload.user.dto.UserDto.Response.Info;
import com.ridingmate.api.repository.UserRepository;
import com.ridingmate.api.security.JwtTokenProvider;
import com.ridingmate.api.service.common.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Transactional
    public AuthResponse normalJoin(NormalUserDto.Request.Join request) {
        if (userRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new CustomException(ResponseCode.DUPLICATE_USER);
        }

        NormalUserEntity normalUser = userRepository.save(new NormalUserEntity(
                request.getUserId(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                UserRole.ROLE_USER));

        return new AuthResponse(jwtTokenProvider.generateToken(normalUser.getUserId(), normalUser.getIdx(), false));
    }

    @Transactional
    public AuthResponse normalLogin(NormalUserDto.Request.Login request) {
        NormalUserEntity normalUser = userRepository.findByUserId(request.getUserId()).orElseThrow(()
                -> new CustomException(ResponseCode.NOT_FOUND_USER));
        if (!passwordEncoder.matches(request.getPassword(), normalUser.getPassword())) {
            throw new CustomException(ResponseCode.NOT_MATCH_USER_INFO);
        }
        return new AuthResponse(jwtTokenProvider.generateToken(normalUser.getUserId(), normalUser.getIdx(), false));
    }

    /**
     * 게시글, 댓글, 북마크 카운트 조회<br>
     * Lazy Loading 문제로 2번 조회
     * @param user      유저 엔티티
     * @return          카운트
     */
    @Transactional(readOnly = true)
    public UserDto.Response.Count getBoardCount(UserEntity user) {
        user = userRepository.findById(user.getIdx()).get();
        return Count.builder()
                    .tradeCount(user.getTradePosts().size())
                    .commentCount(user.getComments().size())
                    .bookmarkCount(user.getBookmarks().size())
                    .build();
    }

    @Transactional
    public UserDto.Response.Info updateUserInfo(UserDto.Request.Update dto, UserEntity user) {
        user.updateInfo(dto.getNickname(), dto.getPhoneNumber());
        if (dto.getProfileFile() != null) {
            try {
                FileEntity file = fileService.uploadFile(dto.getProfileFile(), user);
                file.connectUser(user);
                user.setProfileImageUrl(file.getLocation());
            } catch (Exception e) {
                throw new CustomException(ResponseCode.DONT_SAVE_S3_FILE);
            }
        }
        UserEntity updateUser = userRepository.save(user);
        return Info.of(updateUser);
    }

}
