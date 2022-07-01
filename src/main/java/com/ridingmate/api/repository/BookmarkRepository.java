package com.ridingmate.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.BookmarkEntity;
import com.ridingmate.api.entity.UserEntity;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long>, QuerydslPredicateExecutor<BookmarkEntity> {

    Optional<BookmarkEntity> findByBoardAndUser(BoardEntity board, UserEntity user);

    Boolean existsByBoardAndUser(BoardEntity board, UserEntity user);
}
