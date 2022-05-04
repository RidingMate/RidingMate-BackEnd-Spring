package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.payload.user.request.BikeUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "model")
    private String model;

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

    //바이크 별명
    @Column(name = "bike_nickname")
    private String bikeNickname;

    @Enumerated(EnumType.STRING)
    private BikeRole bikeRole;

    // 연비 기록
    @OneToMany(mappedBy = "bike")
    private List<FuelEntity> fuels = new ArrayList<>();

    // 정비 기록
    @OneToMany(mappedBy = "bike")
    private List<MaintenanceEntity> maintenances = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    //바이크 등록
    public static BikeEntity createBike(UserEntity userEntity, String company, String model, int year, int mileage, String bikeNickname, BikeRole bikeRole){

        //바이크 별명을 입력하지 않았을 경우
        String nickname = null;
        if(bikeNickname.equals("") || bikeNickname == null){
            nickname = model;
        }else{
            nickname = bikeNickname;
        }

        return BikeEntity.builder()
                .user(userEntity)
                .company(company)
                .model(model)
                .year(year)
                .mileage(mileage)
                .bikeNickname(nickname)
                .fuels(new ArrayList<>())
                .maintenances(new ArrayList<>())
                .bikeRole(bikeRole)
                .build();
    }

    //바이크 업데이트
    public void updateBike(BikeUpdateRequest request, BikeRole bikeRole){
        this.company = request.getCompany();
        this.model = request.getModel();
        this.year = request.getYear();
        this.mileage = request.getMileage();
        this.bikeNickname = request.getBikeNickName();
        this.bikeRole = bikeRole;
    }

    //바이크 권한 변경
    public void changeBikeRole(BikeRole bikeRole){
        this.bikeRole = bikeRole;
    }

    public boolean checkBikeRole(){
        if(bikeRole == BikeRole.REPRESENTATIVE) return true;
        return false;
    }



}
