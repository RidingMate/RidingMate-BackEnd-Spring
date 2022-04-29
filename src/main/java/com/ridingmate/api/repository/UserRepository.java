package com.ridingmate.api.repository;

import com.ridingmate.api.entity.NormalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<NormalUserEntity, Long> {


}
