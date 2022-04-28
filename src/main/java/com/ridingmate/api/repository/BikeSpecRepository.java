package com.ridingmate.api.repository;

import com.ridingmate.api.entity.BikeSpecEntity;
import com.ridingmate.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BikeSpecRepository extends JpaRepository<BikeSpecEntity, Long> {


}
