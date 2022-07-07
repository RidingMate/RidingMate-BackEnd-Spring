package com.ridingmate.api.repository.predicate;

import static com.ridingmate.api.entity.QSocialUserEntity.socialUserEntity;

import com.querydsl.core.types.Predicate;

public class UserPredicate extends CommonPredicate {

    public static Predicate isEqualOAuth2Code(String oAuth2Code) {
        return isEq(socialUserEntity.oAuth2Code, oAuth2Code);
    }
}
