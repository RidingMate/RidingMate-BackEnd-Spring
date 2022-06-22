package com.ridingmate.api.repository.predicate;

import static com.ridingmate.api.entity.QTradeBoardEntity.tradeBoardEntity;

import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.ridingmate.api.entity.value.TradeStatus;
import com.ridingmate.api.payload.user.dto.BoardDto;

public class BoardPredicate extends CommonPredicate {

    public static Predicate tradeBoardPredicate(BoardDto.Request.TradeList dto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(dto.getCompany())) {
            builder.and(isStringEq(tradeBoardEntity.company, dto.getCompany()));
        }
        if (StringUtils.hasText(dto.getModelName())) {
            builder.and(isStringEq(tradeBoardEntity.modelName, dto.getModelName()));
        }
        if (StringUtils.hasText(dto.getLocationCode())) {
            builder.and(isStringEq(tradeBoardEntity.location().locationCode, dto.getLocationCode()));
        }
        builder.and(isNumberBetween(tradeBoardEntity.mileage, dto.getMinMileage(), dto.getMaxMileage()));
        builder.and(isNumberBetween(tradeBoardEntity.price, dto.getMinPrice(), dto.getMaxPrice()));
        builder.and(isNumberBetween(tradeBoardEntity.year, dto.getMinYear(), dto.getMaxYear()));
        builder.and(isNumberBetween(tradeBoardEntity.cc, dto.getMinCc(), dto.getMaxCc()));
        if (dto.getHideComplete().equals("Y")) {
            builder.and(isEnumNe(tradeBoardEntity.status, TradeStatus.COMPLETED));
        }
        return builder;
    }

    public static Predicate noticeBoardPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        return builder;
    }
}
