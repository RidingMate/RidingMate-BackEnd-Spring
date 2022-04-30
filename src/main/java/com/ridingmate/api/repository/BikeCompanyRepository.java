package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeSpecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BikeCompanyRepository extends JpaRepository<BikeCompanyEntity, Long> {

    boolean existsByCompany(String company);

    Optional<BikeCompanyEntity> findByCompany(String company);
}
