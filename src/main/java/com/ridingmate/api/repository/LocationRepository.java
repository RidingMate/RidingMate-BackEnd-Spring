package com.ridingmate.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ridingmate.api.entity.LocationEntity;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, String> {

    List<LocationEntity> findByUpperLocationCode(String upperLocationCode);

    List<LocationEntity> findByLocationCodeEndsWithOrderByLocationCode(String locationCode);
}
