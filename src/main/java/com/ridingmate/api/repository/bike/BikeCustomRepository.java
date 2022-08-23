package com.ridingmate.api.repository.bike;

import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface BikeCustomRepository {
    List<BikeEntity> myBikeList(UserEntity user);

    Optional<BikeEntity> myBikeDetail(long bikeIdx, UserEntity user);
}
