package com.ridingmate.api.repository;

import com.ridingmate.api.entity.NoticeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity, Long>, QuerydslPredicateExecutor<NoticeBoardEntity> {
}
