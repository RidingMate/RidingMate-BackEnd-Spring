package com.ridingmate.api.entity;

import com.ridingmate.api.payload.BikeSearchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(name = "bike_company_id")
    private BikeCompanyEntity bikeCompany;

    @OneToMany(mappedBy = "bikeModel")
    private Set<BikeYearEntity> bikeYearSet = new HashSet<>();


    public static BikeModelEntity createBikeModel(String model, BikeCompanyEntity bikeCompany) {
        BikeModelEntity bikeModel = BikeModelEntity.builder()
                .model(model)
                .bikeCompany(bikeCompany)
                .bikeYearSet(new HashSet<>())
                .build();
        bikeCompany.addBikeModel(bikeModel);
        return bikeModel;
    }

    public BikeSearchDto getBikeModelDto(){
        return BikeSearchDto.builder()
                .content(model)
                .build();
    }

}
