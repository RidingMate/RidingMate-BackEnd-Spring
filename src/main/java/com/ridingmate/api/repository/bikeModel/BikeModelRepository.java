package com.ridingmate.api.repository.bikeModel;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.BikeYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BikeModelRepository extends JpaRepository<BikeModelEntity, Long>, BikeModelCustomRepository {

    boolean existsByModelAndBikeCompany(String model, BikeCompanyEntity bikeCompanyEntity);

    Optional<BikeModelEntity> findByModelAndBikeCompany(String model, BikeCompanyEntity bikeCompany);
}
