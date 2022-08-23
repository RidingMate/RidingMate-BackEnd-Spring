package com.ridingmate.api.repository.bikeCompany;

import com.ridingmate.api.entity.BikeCompanyEntity;

import java.util.Optional;

public interface BikeCompanyCustomRepository {
    Optional<BikeCompanyEntity> searchModel(String company);
}
