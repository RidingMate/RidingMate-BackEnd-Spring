package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.BikeYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BikeModelRepository extends JpaRepository<BikeModelEntity, Long> {

    boolean existsByModelAndBikeCompany(String model, BikeCompanyEntity bikeCompanyEntity);

    BikeModelEntity findByModelAndBikeCompany(String model, BikeCompanyEntity bikeCompany);
}
