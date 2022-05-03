package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.TradeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("TRADE")
@NoArgsConstructor
public class TradeBoardEntity extends BoardEntity {

    /**
     * 거래글 게시판 엔티티
     * 거래글에 필요한 데이터나 기능 있으면 추가
     */

    //제조사
    @Column(name = "company")
    private String company;

    //모델명
    @Column(name = "model_name")
    private String modelName;

    //연비
    @Column(name = "fuel_efficiency")
    private double fuelEfficiency;

    //배기량
    @Column(name = "cc")
    private String cc;

    //연식
    @Column(name = "year")
    private String year;

    //주행거리
    @Column(name = "mileage")
    private String mileage;

    //가격
    @Column(name = "price")
    private String price;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_status")
    private TradeStatus status;

    // TODO : 내 바이크를 올릴경우 내 바이크와 연관관계 필요

    // TODO : 직거래를 위한 지역에 대한 연관관계도 필요할듯

    public TradeBoardEntity(String title,
                            String company,
                            String modelName,
                            double fuelEfficiency,
                            String cc,
                            String year,
                            String mileage,
                            String price
                            ) {

        this.company = company;
        this.modelName = modelName;
        this.fuelEfficiency = fuelEfficiency;
        this.cc = cc;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.status = TradeStatus.FOR_SALE;
        createBoardEntity(title);
    }
}
