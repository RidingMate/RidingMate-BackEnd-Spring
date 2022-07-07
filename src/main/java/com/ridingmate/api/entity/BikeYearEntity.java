package com.ridingmate.api.entity;

import com.ridingmate.api.payload.user.dto.BikeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_model_id")
    private BikeModelEntity bikeModel;


    public static BikeYearEntity createBikeYear(int year, BikeModelEntity bikeModelEntity) {
        BikeYearEntity bikeYear = BikeYearEntity.builder()
                .year(year)
                .bikeModel(bikeModelEntity)
                .build();
        return bikeYear;
    }

    public BikeDto.Request.BikeSearch getBikeYearDto(){
        return BikeDto.Request.BikeSearch.builder()
                .content(year+"")
                .build();
    }
}
