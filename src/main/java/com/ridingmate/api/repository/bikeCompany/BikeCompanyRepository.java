package com.ridingmate.api.repository.bikeCompany;

import com.ridingmate.api.entity.BikeCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BikeCompanyRepository extends JpaRepository<BikeCompanyEntity, Long>, BikeCompanyCustomRepository {

    boolean existsByCompany(String company);

    Optional<BikeCompanyEntity> findByCompany(String company);


}
