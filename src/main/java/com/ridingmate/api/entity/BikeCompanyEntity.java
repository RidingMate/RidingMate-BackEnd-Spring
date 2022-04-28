package com.ridingmate.api.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_company_idx")
    private List<BikeModelEntity> bikeModelEntities = new ArrayList<>();

    public void createBikeModelList(){
        bikeModelEntities = new ArrayList<>();
    }

}
