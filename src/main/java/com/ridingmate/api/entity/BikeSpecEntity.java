package com.ridingmate.api.entity;

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
public class BikeSpecEntity {

    /*
        모든 바이크의 정보를 저장하는 Table
        TODO : 어떤 정보 뽑아서 사용할 수 있는지 확인해야함

        데이터 정리 :
            json : detail

            company : 제조사

            model : 모델명

            year : 연식

            power HP : 마력

            bore mm : 보어
            stroke mm : 스트로크
                -> 표기 : 보어 x 스트로크 로 표기함

            power benchmark rpm : 최대 rpm
            torque nm / 토크 / torque_nm
                ->표기 : 토크/rpm

            compression ratio : 압축비

            overall height mm : 전고

            overall width mm : 전폭

            overall length mm : 전장

            seat height mm : 시트고

            wheelbase mm : 휠 베이스

            dry weight kg : 차량 중량 (연료, 오일 완충)

            fuel capacity liters : 연료 탱크 용량

            front tyre : 프론트 타이어

            rear tyre : 리어 타이어

            gearbox : 기어 단수

            front suspension : 프론트 서스펜션
                    ex) 도립식 포크 (직경 41mm)

            rear suspension : 리어 서스펜션
                    ex) 알루미늄 듀얼 스윙암

            front brakes : 프론트 브레이크
                    ex) 싱글 디스크 브레이크 300mm 4피스톤 ABS

            rear brakes : 리어 브레이크

            category : 카테고리

            engine type : 엔진 타입

            fuel system : 연료 타입 (인젝션 or 캬브레터)

            cooling system : 냉각 시스템
                    ex ) Liquid : 수랭
                         Air : 공랭

            displacement ccm : cc

     */

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

    @Column(name = "power_HP")
    private double powerHP;

    @Column(name = "bore_mm")
    private double boreMm;

    @Column(name = "stroke_mm")
    private double strokeMm;

    @Column(name = "power_benchmark_rpm")
    private int powerBenchmarkRpm;

    @Column(name = "torque_nm")
    private double torqueNm;

    @Column(name = "compression_ratio")
    private String compressionRatio;

    @Column(name = "overall_height_mm")
    private String overallHeightMm;

    @Column(name = "overall_width_mm")
    private String overallWidthMm;

    @Column(name = "overall_length_mm")
    private String overallLengthMm;

    @Column(name = "seat_height_mm")
    private String seatHeightMm;

    @Column(name = "wheelbase_mm")
    private String wheelbaseMm;

    @Column(name = "dry_weight_kg")
    private String dryWeightKg;

    @Column(name = "fuel_capacity_liters")
    private String fuelCapacityLiters;

    @Column(name = "front_tyre")
    private String frontTyre;

    @Column(name = "rear_tyre")
    private String rearTyre;

    @Column(name = "gearbox")
    private String gearbox;

    @Column(name = "front_suspension")
    private String frontSuspension;

    @Column(name = "rear_suspension", columnDefinition = "LONGTEXT")
    private String rearSuspension;

    @Column(name = "front_brakes")
    private String frontBrakes;

    @Column(name = "rear_brakes")
    private String rearBrakes;

    @Column(name = "category")
    private String category;

    @Column(name = "engine_type")
    private String engineType;

    @Column(name = "fuel_system", columnDefinition = "LONGTEXT")
    private String fuelSystem;

    @Column(name = "cooling_system")
    private String coolingSystem;

    @Column(name = "displacement_ccm")
    private String displacementCcm;

}
