package com.ridingmate.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ridingmate.api.entity.SocialUserEntity;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUserEntity, Long>, QuerydslPredicateExecutor<SocialUserEntity> {
}
