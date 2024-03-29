package com.ridingmate.api.repository.bike;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.entity.value.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BikeRepository extends JpaRepository<BikeEntity, Long>, BikeCustomRepository {
    Optional<BikeEntity> findByIdxAndUser(long idx, UserEntity user);

    List<BikeEntity> findByUserAndBikeRole(UserEntity userEntity, BikeRole bikeRole);

//    List<BikeEntity> findByUserOrderByBikeRole(UserEntity userEntity);

    List<BikeEntity> findByUser(UserEntity user);

    List<BikeEntity> findByUserOrderByBikeRole(UserEntity user);
}
