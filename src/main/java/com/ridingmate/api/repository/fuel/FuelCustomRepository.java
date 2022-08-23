package com.ridingmate.api.repository.fuel;

import com.ridingmate.api.entity.FuelEntity;
import com.ridingmate.api.entity.UserEntity;

import java.util.List;

public interface FuelCustomRepository {
    List<FuelEntity> fuelList(long bikeIdx, UserEntity user);
    long fuelReset(long bikeIdx, UserEntity user);
}
