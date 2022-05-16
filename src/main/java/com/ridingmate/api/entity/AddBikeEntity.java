package com.ridingmate.api.entity;

import com.ridingmate.api.payload.user.request.AddBikeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "RMC_BIKE_SPEC")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
public class AddBikeEntity {


    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "company")
    private String company;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public AddBikeEntity convertRequestToEntity(AddBikeRequest addBikeRequest, UserEntity user){
        return AddBikeEntity.builder()
                .company(addBikeRequest.getCompany())
                .model(addBikeRequest.getModel())
                .year(addBikeRequest.getYear())
                .user(user)
                .build();
    }

}
