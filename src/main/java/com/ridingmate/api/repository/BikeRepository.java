package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BikeRepository extends JpaRepository<BikeEntity, Long> {


}
