package com.ridingmate.api.repository.fuel;

import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FuelRepository extends JpaRepository<FuelEntity, Long>, FuelCustomRepository{

    @EntityGraph(attributePaths = {"bike"})
    List<FuelEntity> findByBikeOrderByCreateAt(BikeEntity bikeEntity);

    List<FuelEntity> findByBikeAndReset(BikeEntity bikeEntity, char reset);
}
