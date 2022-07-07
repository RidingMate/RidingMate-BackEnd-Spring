package com.ridingmate.api.repository;

import static com.ridingmate.api.entity.QCommentEntity.commentEntity;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepository {

    private final EntityManager entityManager;

    /**
     * 내가 댓글 남긴 게시글 조회 쿼리
     * @param user      현재 로그인된 유저
     * @param pageable  page
     * @return          BoardEntity
     */
    public Page<BoardEntity> getMyCommentBoardList(UserEntity user, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<BoardEntity> query = queryFactory
                .select(commentEntity.board())
                .from(commentEntity)
                .where(commentEntity.user().eq(user))
                .groupBy(commentEntity.board().idx);
        query.offset(pageable.getOffset())
             .limit(pageable.getPageSize());
        return new PageImpl<>(query.fetch(), pageable, getMyCommentBoardCount(user));
    }

    private long getMyCommentBoardCount(UserEntity user) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory
                .select(Wildcard.count)
                .from(commentEntity)
                .where(commentEntity.user().eq(user)).fetch().size();
    }
}
