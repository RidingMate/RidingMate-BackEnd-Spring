package com.ridingmate.api.repository.predicate;

import java.time.LocalDateTime;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;

public class CommonPredicate {

    public static Predicate isEq(StringPath column, String text) {
        return column.eq(text);
    }

    public static Predicate isEq(NumberPath column, Number number) {
        return column.eq(number);
    }

    public static Predicate isEq(EnumPath column, Enum e) {
        return column.eq(e);
    }

    public static Predicate isBetween(NumberPath column, Number from, Number to) {
        return column.between(from, to);
    }

    public static Predicate isBetween(DateTimePath<LocalDateTime> column, LocalDateTime from,
                                      LocalDateTime to) {
        return column.between(from, to);
    }

    public static Predicate isNe(StringPath column, String text) {
        return column.ne(text);
    }

    public static Predicate isNe(NumberPath column, Number number) {
        return column.ne(number);
    }

    public static Predicate isNe(EnumPath column, Enum e) {
        return column.ne(e);
    }

    public static Predicate isNull(SimpleExpression column) {
        return column.isNull();
    }

    public static Predicate isNotNull(SimpleExpression column) {
        return column.isNotNull();
    }

}
