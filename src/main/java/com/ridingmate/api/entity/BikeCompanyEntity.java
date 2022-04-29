package com.ridingmate.api.entity;

import com.ridingmate.api.payload.BikeCompanyDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Table(name = "RMC_BIKE_COMPANY")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
public class BikeCompanyEntity {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "company")
    private String company;

    @OneToMany(mappedBy = "bikeCompany")
    private Set<BikeModelEntity> bikeModelSet = new HashSet<>();

    public void addBikeModel(BikeModelEntity bikeModel) {
        bikeModelSet.add(bikeModel);
    }

    public static BikeCompanyEntity createBikeCompany(String company) {
        BikeCompanyEntity bikeCompany = BikeCompanyEntity.builder()
                .company(company)
                .bikeModelSet(new HashSet<>())
                .build();
        return bikeCompany;
    }

    public BikeCompanyDto getBikeCompanyDto(){
        return BikeCompanyDto.builder()
                .idx(idx)
                .company(company)
                .build();
    }
}
