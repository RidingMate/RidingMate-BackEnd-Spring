package com.ridingmate.api.entity;

import com.ridingmate.api.payload.user.request.AddFuelRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name = "RMC_FUEL")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class FuelEntity extends BaseTime {

    /*
        내 바이크의 연비를 기록하는 Table정
        나의 바이크에서 연관관계 설정

        연비 관리 -> 주유량, 현재 주행거리, 주유 가격 입력
            if(이전 주행거리가 존재한다면)
                현재 주행거리 - 이전 주행거리 (운행 km)
                주유량
                -> 위 두가지를 이용해서 1L 당 몇 km 를 운행했는지 계산
        문제) 기름을 항상 가득 주유함을 전재로 해야만 계산이 가능함
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    //이전 주행거리 -> 이전 기록에서 가져와서 저장할 예정
    @Column(name = "previous_mileage")
    private int previousMileage;

    //현재 주행거리
    //TODO : 현재 주행거리 입력 시 내 바이크 주행거리도 최신화 해야함
    @Column(name = "recent_mileage")
    private int recentMileage;

    //주유량
    @Column(name = "fuel_volume")
    private int fuelVolume;

    //주유 가격
    @Column(name = "fuelAmount")
    private int fuelAmount;

    //초기화 여부
    @Column(name = "reset")
    private char reset;

    //연비
    @Column(name = "fuel_efficiency")
    private double fuelEfficiency;

    //연비
    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id")
    private BikeEntity bike;


    public FuelEntity createEntity(BikeEntity bikeEntity, AddFuelRequest addFuelRequest){
        this.previousMileage = bike.getMileage();
        this.recentMileage = addFuelRequest.getMileage();
        this.fuelVolume = addFuelRequest.getFuelVolume();
        this.fuelAmount = addFuelRequest.getAmount();
        this.reset = 'N';
        this.bike = bikeEntity;
        this.date = addFuelRequest.getDate();
        this.fuelEfficiency = (this.recentMileage - this.previousMileage) / this.fuelVolume;
        return this;
    }


}
