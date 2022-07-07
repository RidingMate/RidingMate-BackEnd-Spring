package com.ridingmate.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.SocialUserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.repository.SocialUserRepository;
import com.ridingmate.api.repository.UserRepository;
import com.ridingmate.api.repository.predicate.UserPredicate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;

    /**
     * 유저 인증 객체 생성
     * @param userId                        유저 구분자(일반 - 아이디, 소셜 - 소셜 코드)
     * @return                              UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userId) {
        NormalUserEntity user = userRepository.findByUserId(userId).orElse(null);
        if (user == null) {
            SocialUserEntity socialUser = socialUserRepository.findOne(UserPredicate.isEqualOAuth2Code(userId))
                                                              .orElseThrow(() -> new CustomException(
                                                                      ResponseCode.NOT_FOUND_USER));
            return UserPrincipal.create(socialUser, null);
        }
        return UserPrincipal.create(user);
    }

}
