package com.ridingmate.api.repository;

import com.ridingmate.api.entity.TradeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeBoardRepository extends JpaRepository<TradeBoardEntity, Long> {
}
