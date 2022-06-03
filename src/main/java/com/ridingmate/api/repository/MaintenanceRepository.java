package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {

    List<MaintenanceEntity> findByBike(BikeEntity bike);

    MaintenanceEntity findByIdxAndBike(Long idx, BikeEntity bike);

}
