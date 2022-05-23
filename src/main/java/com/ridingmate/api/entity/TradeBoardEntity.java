package com.ridingmate.api.entity;

import java.time.LocalDate;

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
    private int cc;

    //연식
    @Column(name = "year")
    private int year;

    //주행거리
    @Column(name = "mileage")
    private int mileage;

    // 구매 일자
    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;

    //가격
    @Column(name = "price")
    private int price;

    // 연락처
    @Column(name = "phone_number")
    private String phoneNumber;

    // 구매자에게 주유/정비정보 공개
    @Column(name = "is_open_to_buyer")
    private Character isOpenToBuyer;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_status")
    private TradeStatus status;

    // 내 바이크를 올릴경우 내 바이크와 연관관계 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_bike_idx")
    private BikeEntity myBike;

    // 직거래를 위한 지역에 대한 연관관계도 필요할듯
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_code")
    private LocationEntity location;

    // TODO : 썸네일 컬럼 추가
    // TODO : 썸네일 저장을 위한 location 저장 컬럼
    // File에 대한 entity 필요할거같음 생성해서 연관관계 연결

    // 작성자 없는 생성자
    public TradeBoardEntity(String title,
                            String company,
                            String modelName,
                            double fuelEfficiency,
                            int cc,
                            int year,
                            int mileage,
                            int price
    ) {

        this.company = company;
        this.modelName = modelName;
        this.fuelEfficiency = fuelEfficiency;
        this.cc = cc;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        status = TradeStatus.FOR_SALE;
        createBoardEntity(title);
    }

    // 작성자 있는 생성자
    public TradeBoardEntity(String title,
                            String company,
                            String modelName,
                            double fuelEfficiency,
                            int cc,
                            int year,
                            int mileage,
                            int price,
                            UserEntity user
    ) {

        this.company = company;
        this.modelName = modelName;
        this.fuelEfficiency = fuelEfficiency;
        this.cc = cc;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        status = TradeStatus.FOR_SALE;
        createBoardEntity(title, user);
    }

    // 작성자, 내바이크 생성자
    public TradeBoardEntity(String title, String content, UserEntity user, BikeEntity bike) {
        myBike = bike;
        createBoardEntity(title, content, user);
    }

    // 작성자, 내바이크, 지역 생성자
    public TradeBoardEntity(String title, String content, UserEntity user, BikeEntity bike,
                            LocationEntity location) {
        myBike = bike;
        this.location = location;
        createBoardEntity(title, content, user);
    }

    // 작성자 + 지역 생성자
    public TradeBoardEntity(String title,
                            String company,
                            String modelName,
                            double fuelEfficiency,
                            int cc,
                            int year,
                            int mileage,
                            int price,
                            UserEntity user,
                            LocationEntity location
    ) {

        this.company = company;
        this.modelName = modelName;
        this.fuelEfficiency = fuelEfficiency;
        this.cc = cc;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.location = location;
        status = TradeStatus.FOR_SALE;
        createBoardEntity(title, user);
    }

    // 작성자 + 지역 + 구매일자 생성자
    public TradeBoardEntity(String title,
                            String company,
                            String modelName,
                            double fuelEfficiency,
                            int cc,
                            int year,
                            int mileage,
                            int price,
                            Integer purchaseYear,
                            Integer purchaseMonth,
                            UserEntity user,
                            LocationEntity location
    ) {

        this.company = company;
        this.modelName = modelName;
        this.fuelEfficiency = fuelEfficiency;
        this.cc = cc;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.location = location;
        if (purchaseYear != null && purchaseMonth != null) {
            dateOfPurchase = LocalDate.of(purchaseYear, purchaseMonth, 1);
        }
        status = TradeStatus.FOR_SALE;
        createBoardEntity(title, user);
    }

    // 작성자 + 내용 + 지역 + 구매일자 생성자
    public TradeBoardEntity(String title,
                            String content,
                            String company,
                            String modelName,
                            double fuelEfficiency,
                            int cc,
                            int year,
                            int mileage,
                            int price,
                            Integer purchaseYear,
                            Integer purchaseMonth,
                            UserEntity user,
                            LocationEntity location
    ) {

        this.company = company;
        this.modelName = modelName;
        this.fuelEfficiency = fuelEfficiency;
        this.cc = cc;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.location = location;
        if (purchaseYear != null && purchaseMonth != null) {
            dateOfPurchase = LocalDate.of(purchaseYear, purchaseMonth, 1);
        }
        status = TradeStatus.FOR_SALE;
        createBoardEntity(title, content, user);
    }

    // 예약중 상태
    public void setReservedStatus() {
        status = TradeStatus.RESERVED;
    }

    // 판매완료 상태
    public void setCompletedStatus() {
        status = TradeStatus.COMPLETED;
    }
}
