package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.SocialType;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("SOCIAL")
public class SocialUserEntity extends UserEntity {

    /**
     * 소셜 유저 엔티티
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;
}
