package com.ridingmate.api.repository.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.Wildcard;

public class CommonPredicate {

    public static Predicate isStringEq(StringPath column, String text) {
        return column.eq(text);
    }

    public static Predicate isNumberBetween(NumberPath column, Number from, Number to) {
        return column.between(from, to);
    }

    public static Predicate isStringNe(StringPath column, String text) {
        return column.ne(text);
    }

    public static Predicate isNumberEq(NumberPath column, Number number) {
        return column.eq(number);
    }

    public static Predicate isNumberNe(NumberPath column, Number number) {
        return column.ne(number);
    }

    public static Predicate isEnumEq(EnumPath column, Enum e) {
        return column.eq(e);
    }

    public static Predicate isEnumNe(EnumPath column, Enum e) {
        return column.ne(e);
    }

    public static Predicate isNull(SimpleExpression column) {
        return column.isNull();
    }

    public static Predicate isNotNull(SimpleExpression column) {
        return column.isNotNull();
    }

    public static NumberExpression<Long> wildcardCount() {
        return Wildcard.count;
    }
}
