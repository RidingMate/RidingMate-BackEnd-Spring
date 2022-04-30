package com.ridingmate.api.repository;

import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<NormalUserEntity> findByUserId(String userId);

    Optional<UserEntity> findByUserUuid(String uuid);

}
