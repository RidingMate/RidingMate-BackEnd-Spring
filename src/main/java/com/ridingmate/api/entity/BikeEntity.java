package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.payload.user.dto.BikeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "RMC_BIKE")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
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
    private double fuelEfficiency;

    //주유 횟수
    @Column(name = "count_oiling")
    private int countOiling;

    //정비 횟수
    @Column(name = "count_maintenance")
    private int countMaintenance;

    //바이크 별명
    @Column(name = "bike_nickname")
    private String bikeNickname;

    //대표 바이크 여부
    @Enumerated(EnumType.STRING)
    private BikeRole bikeRole;

    //구입날짜
    @CreationTimestamp
    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;


    // 연비 기록
    @OneToMany(mappedBy = "bike", orphanRemoval = true)
    private List<FuelEntity> fuels = new ArrayList<>();

    // 정비 기록
    @OneToMany(mappedBy = "bike", orphanRemoval = true)
    private List<MaintenanceEntity> maintenances = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    //이미지
    @OneToOne(mappedBy = "bike", fetch = FetchType.LAZY)
    private FileEntity fileEntity;


    //바이크 등록
    public BikeEntity createBike(UserEntity userEntity, BikeRole bikeRole, BikeDto.Request.BikeInsert request){

        //바이크 별명을 입력하지 않았을 경우
        String nickname = null;
        if(request.getBikeNickName().equals("") || request.getBikeNickName() == null){
            nickname = model;
        }else{
            nickname = bikeNickname;
        }

        return BikeEntity.builder()
                .user(userEntity)
                .company(request.getCompany())
                .model(request.getModel())
                .year(request.getYear())
                .mileage(request.getMileage())
                .bikeNickname(nickname)
                .fuels(new ArrayList<>())
                .maintenances(new ArrayList<>())
                .bikeRole(bikeRole)
                .dateOfPurchase(request.getDateOfPurchase())
                .build();
    }

    //바이크 업데이트
    public void updateBike(BikeDto.Request.BikeUpdate request, BikeRole bikeRole){
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

    //바이크 권한 확인
    public boolean checkBikeRole(){
        if(bikeRole == BikeRole.REPRESENTATIVE) return true;
        return false;
    }

    //연비 추가
    //현재 주행거리 최신화
    //연비 - 초기화 되지 않은 연비 받아서 저장
    public void addFuel(int recentMileage, double totalFuelEfficiency, int countOiling){
        this.countOiling++;
        this.mileage = recentMileage;
        this.fuelEfficiency = totalFuelEfficiency/(double)countOiling;
    }

    public void resetFuel(){
        this.fuelEfficiency = 0;
    }

    // 정비 횟수 증가, 감소 메소드 만들기
    public void countUpMaintenance(){
        this.countMaintenance ++;
    }
    public void countDownMaintenance(){
        this.countMaintenance --;
    }
}
