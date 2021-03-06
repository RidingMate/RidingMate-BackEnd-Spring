package com.ridingmate.api.entity;

import com.ridingmate.api.payload.user.dto.BikeDto;
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

    public BikeDto.Request.BikeSearch getBikeCompanyDto(){
        return BikeDto.Request.BikeSearch.builder()
                .content(company)
                .build();
    }
}
