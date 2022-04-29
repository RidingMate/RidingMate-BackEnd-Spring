package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.SocialType;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DiscriminatorValue("SOCIAL")
public class SocialUserEntity extends UserEntity {

    /**
     * 소셜 유저 엔티티
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    // 소셜 리소스 서버에서 넘겨주는 식별자
    @Column(name = "oAuth2_code", unique = true, nullable = false)
    private String oAuth2Code;

}
