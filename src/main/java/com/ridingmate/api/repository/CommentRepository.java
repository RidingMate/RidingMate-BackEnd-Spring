package com.ridingmate.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, QuerydslPredicateExecutor<CommentEntity> {

    Page<CommentEntity> findByBoardAndParentCommentIdxIsNull(BoardEntity board, Pageable pageable);
}
