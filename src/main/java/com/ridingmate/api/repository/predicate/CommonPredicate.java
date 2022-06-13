package com.ridingmate.api.repository.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

public class CommonPredicate {

    public static Predicate isEq(StringPath column, String text) {
        return column.eq(text);
    }

    public static Predicate isBetween(NumberPath column, Number from, Number to) {
        return column.between(from, to);
    }

    public static Predicate isNe(StringPath column, String text) {
        return column.ne(text);
    }
}
