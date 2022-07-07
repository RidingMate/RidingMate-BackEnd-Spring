package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {

    MaintenanceEntity findByIdxAndBike(Long idx, BikeEntity bike);

    List<MaintenanceEntity> findByBikeAndDateOfMaintenanceBetween(BikeEntity bike, LocalDate startDate, LocalDate endDate);

}
