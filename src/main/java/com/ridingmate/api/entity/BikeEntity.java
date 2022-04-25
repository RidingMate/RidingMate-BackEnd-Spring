package com.ridingmate.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "RMC_BIKE")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class BikeEntity extends BaseTime{

    /*
        내 바이크를 표현하는 Table
        내 바이크는 BikeSpec Table 에서 값을 가져올 예정
    */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    //제조사
    @Column(name = "company")
    private String company;

    //모델명
    @Column(name = "model_name")
    private String modelName;

    //년식
    @Column(name = "year")
    private int year;

    //주행거리
    @Column(name = "mileage")
    private int mileage;

    //연비
    @Column(name = "fuel_efficiency")
    private int fuelEfficiency;

    //주유 횟수
    @Column(name = "count_oiling")
    private int countOiling;

    //정비 횟수
    @Column(name = "count_maintenance")
    private int countMaintenance;
}
