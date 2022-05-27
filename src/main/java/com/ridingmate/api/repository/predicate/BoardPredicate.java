package com.ridingmate.api.repository.predicate;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.ridingmate.api.payload.user.request.TradeSearchRequest;

public class BoardPredicate {

    public static Predicate tradeBoardPredicate(TradeSearchRequest request) {
        BooleanBuilder builder = new BooleanBuilder();
        return builder;
    }

    public static Predicate noticeBoardPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        return builder;
    }
}
