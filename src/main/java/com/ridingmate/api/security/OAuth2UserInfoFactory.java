package com.ridingmate.api.security;

import java.util.Map;

import com.ridingmate.api.entity.value.SocialType;

public class OAuth2UserInfoFactory {

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
