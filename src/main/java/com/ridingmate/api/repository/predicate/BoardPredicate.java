package com.ridingmate.api.repository.predicate;

import static com.ridingmate.api.entity.QTradeBoardEntity.tradeBoardEntity;

import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.ridingmate.api.entity.value.TradeStatus;
import com.ridingmate.api.payload.user.dto.BoardDto;
import com.ridingmate.api.payload.user.request.TradeSearchRequest;

public class BoardPredicate {

    public static Predicate tradeBoardPredicate(TradeSearchRequest request) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(request.getCompany())) {
            builder.and(tradeBoardEntity.company.eq(request.getCompany()));
        }
        if (StringUtils.hasText(request.getModelName())) {
            builder.and(tradeBoardEntity.modelName.eq(request.getModelName()));
        }
        if (StringUtils.hasText(request.getLocationCode())) {
            builder.and(tradeBoardEntity.location().locationCode.eq(request.getLocationCode()));
        }
        builder.and(tradeBoardEntity.mileage.between(request.getMinMileage(), request.getMaxMileage()));
        builder.and(tradeBoardEntity.price.between(request.getMinPrice(), request.getMaxPrice()));
        builder.and(tradeBoardEntity.year.between(request.getMinYear(), request.getMaxYear()));
        builder.and(tradeBoardEntity.cc.between(request.getMinCc(), request.getMaxCc()));
        if (request.getHideComplete().equals("Y")) {
            builder.and(tradeBoardEntity.status.ne(TradeStatus.COMPLETED));
        }
        return builder;
    }

    public static Predicate tradeBoardPredicate(BoardDto.Request.TradeList dto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(dto.getCompany())) {
            builder.and(tradeBoardEntity.company.eq(dto.getCompany()));
        }
        if (StringUtils.hasText(dto.getModelName())) {
            builder.and(tradeBoardEntity.modelName.eq(dto.getModelName()));
        }
        if (StringUtils.hasText(dto.getLocationCode())) {
            builder.and(tradeBoardEntity.location().locationCode.eq(dto.getLocationCode()));
        }
        builder.and(tradeBoardEntity.mileage.between(dto.getMinMileage(), dto.getMaxMileage()));
        builder.and(tradeBoardEntity.price.between(dto.getMinPrice(), dto.getMaxPrice()));
        builder.and(tradeBoardEntity.year.between(dto.getMinYear(), dto.getMaxYear()));
        builder.and(tradeBoardEntity.cc.between(dto.getMinCc(), dto.getMaxCc()));
        if (dto.getHideComplete().equals("Y")) {
            builder.and(tradeBoardEntity.status.ne(TradeStatus.COMPLETED));
        }
        return builder;
    }

    public static Predicate noticeBoardPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        return builder;
    }
}
