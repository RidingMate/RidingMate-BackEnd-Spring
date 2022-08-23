package com.ridingmate.api.repository.bikeModel;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeModelEntity;

import java.util.List;

public interface BikeModelCustomRepository {
    List<BikeModelEntity> searchModel(String company);
}
