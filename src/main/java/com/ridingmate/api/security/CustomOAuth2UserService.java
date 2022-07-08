package com.ridingmate.api.security;

import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ridingmate.api.entity.SocialUserEntity;
import com.ridingmate.api.entity.value.SocialType;
import com.ridingmate.api.repository.SocialUserRepository;
import com.ridingmate.api.repository.predicate.UserPredicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    /**
     * 소셜 로그인시 사용
     */

    private final SocialUserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User user = super.loadUser(userRequest);
        log.info("OAuth2UserService OAuth2User : {}", user);

        try {
            return process(userRequest, user);
        } catch (AuthenticationException e) {
            log.error("Authentication Error : {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
            throw e;
        }
    }

    private OAuth2User process(OAuth2UserRequest request, OAuth2User user) {
        SocialType socialType = SocialType.valueOf(
                request.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, user.getAttributes());
        SocialUserEntity socialUser = saveOrUpdate(socialType, oAuth2UserInfo);
        return UserPrincipal.create(socialUser, user.getAttributes());
    }

    private SocialUserEntity saveOrUpdate(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        SocialUserEntity user = userRepository.findOne(UserPredicate.isEqualOAuth2Code(oAuth2UserInfo.getId()))
                                              .orElse(null);
        if (user != null) {
            // 수정되는 내용 추가
        } else {
            // 소셜유저 신규 등록
            user = SocialUserEntity.builder()
                                   .socialType(socialType)
                                   .oAuth2Code(oAuth2UserInfo.getId())
                                   .profileImageUrl(oAuth2UserInfo.getImageUrl())
                                   .build();
            user.createDefaultInfo();
            userRepository.save(user);
        }
        return user;
    }
}
