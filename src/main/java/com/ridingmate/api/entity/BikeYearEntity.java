package com.ridingmate.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Table(name = "RMC_BIKE_YEAR")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
public class BikeYearEntity {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "year")
    private int year;

//    @OneToMany(mappedBy = "bikeYearEntity")
//    List<BikeModelEntity> bikeModelEntities = new ArrayList<>();

    @OneToMany(mappedBy = "bikeYearEntity")
    private Set<BikeModelEntity> bikeModelSet = new HashSet<>();

    public void addBikeModel(BikeModelEntity bikeModel) {
        bikeModelSet.add(bikeModel);
    }

    public static BikeYearEntity createBikeYear(int year) {
        BikeYearEntity bikeYear = BikeYearEntity.builder()
                .year(year)
                .bikeModelSet(new HashSet<>())
                .build();
        return bikeYear;
    }
}
