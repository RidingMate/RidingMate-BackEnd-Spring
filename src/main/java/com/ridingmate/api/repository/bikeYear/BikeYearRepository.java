package com.ridingmate.api.repository.bikeYear;

import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.BikeYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BikeYearRepository extends JpaRepository<BikeYearEntity, Long> , BikeYearCustomRepository{

    boolean existsByYearAndBikeModel(int year, BikeModelEntity bikeModelEntity);

    Optional<BikeYearEntity> findByYearAndBikeModel(int year, BikeModelEntity bikeModelEntity);
}
