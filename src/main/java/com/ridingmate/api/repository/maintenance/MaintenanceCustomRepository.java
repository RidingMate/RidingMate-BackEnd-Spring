package com.ridingmate.api.repository.maintenance;

import com.ridingmate.api.entity.MaintenanceEntity;
import com.ridingmate.api.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MaintenanceCustomRepository{
    List<MaintenanceEntity> list(Long bikeIdx, UserEntity user, LocalDate start, LocalDate end);

    Optional<MaintenanceEntity> maintenanceDetail(Long bikeIdx, Long maintenanceIdx, UserEntity user);
}
