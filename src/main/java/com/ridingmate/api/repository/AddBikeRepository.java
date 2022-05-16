package com.ridingmate.api.repository;

import com.ridingmate.api.entity.AddBikeEntity;
import com.ridingmate.api.entity.BikeSpecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddBikeRepository extends JpaRepository<AddBikeEntity, Long> {

}
