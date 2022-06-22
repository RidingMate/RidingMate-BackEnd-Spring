package com.ridingmate.api.repository.predicate;

import static com.ridingmate.api.entity.QCommentEntity.commentEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.ridingmate.api.entity.BoardEntity;

public class CommentPredicate extends CommonPredicate {

    public static Predicate getComment(BoardEntity board, Long parentCommentId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(commentEntity.board().eq(board));
        if (parentCommentId != null) {
            builder.and(isNumberEq(commentEntity.parentCommentIdx, parentCommentId));
        } else {
            builder.and(isNull(commentEntity.parentCommentIdx));
        }
        return builder;
    }
}
