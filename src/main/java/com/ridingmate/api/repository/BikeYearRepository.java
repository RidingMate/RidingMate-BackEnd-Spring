package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.BikeYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BikeYearRepository extends JpaRepository<BikeYearEntity, Long> {
    boolean existsByYear(int year);

    BikeYearEntity findByYear(int year);

    boolean existsByYearAndBikeModel(int year, BikeModelEntity bikeModelEntity);
}
