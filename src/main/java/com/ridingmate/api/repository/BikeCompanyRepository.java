package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeSpecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BikeCompanyRepository extends JpaRepository<BikeCompanyEntity, Long> {

    boolean existsByCompany(String company);

    BikeCompanyEntity findByCompany(String company);
}
