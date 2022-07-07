package com.ridingmate.api.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ridingmate.api.consts.SocialType;
import com.ridingmate.api.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    /**
     * 소셜 로그인시 사용
     */

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // TODO : 소셜에서 받은 정보와 DB 유저 정보 비교
        OAuth2User user = super.loadUser(userRequest);
        try {
            process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return super.loadUser(userRequest);
    }

    private OAuth2User process(OAuth2UserRequest request, OAuth2User user) {
        SocialType socialType = SocialType.valueOf(
                request.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(socialType, user.getAttributes());

        // TODO : DB에서 유저 조회
        // null이면 신규 등록, 아니면 업데이트
        return null;
    }

    public static OAuth2UserInfo getOAuth2UserInfo(SocialType socialType, Map<String, Object> attributes) {
        switch (socialType) {
            case GOOGLE:
                return new GoogleOAuth2UserInfo(attributes);
            case KAKAO:
                return new KakaoOAuth2UserInfo(attributes);
            default:
                throw new IllegalStateException("잘못된 소셜 타입입니다.");
        }
    }
}
