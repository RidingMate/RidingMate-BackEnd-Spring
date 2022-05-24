package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FuelRepository extends JpaRepository<FuelEntity, Long> {
    List<FuelEntity> findByBikeOrderByCreateAt(BikeEntity bikeEntity);
}
