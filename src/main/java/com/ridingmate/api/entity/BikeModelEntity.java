package com.ridingmate.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "RMC_BIKE_MODEL")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
public class BikeModelEntity {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "model")
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_year_id")
    private BikeYearEntity bikeYearEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_company_id")
    private BikeCompanyEntity bikeCompany;

//    public void setBikeYearEntity(BikeYearEntity bikeYearEntity){
//        this.bikeYearEntity = bikeYearEntity;
//    }

    public static BikeModelEntity createBikeModel(String model, BikeYearEntity bikeYear, BikeCompanyEntity bikeCompany) {
        BikeModelEntity bikeModel = BikeModelEntity.builder()
                .model(model)
                .bikeYearEntity(bikeYear)
                .bikeCompany(bikeCompany)
                .build();
        bikeYear.addBikeModel(bikeModel);
        bikeCompany.addBikeModel(bikeModel);
        return bikeModel;
    }

}
