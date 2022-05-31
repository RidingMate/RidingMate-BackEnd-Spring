package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.payload.user.request.BikeUpdateRequest;
import com.ridingmate.api.payload.user.request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.request.MaintenanceUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "RMC_MAINTENANCE")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
public class MaintenanceEntity extends BaseTime {

    /*
        정비 기록를 저장할 Table
        나의 바이크에서 연관관계로 설정
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 제목
    @Column(name="title", nullable = false)
    private String title;

    // 서비스센터 /기관명
    @Column(name = "area", nullable = false)
    private String area;

    // 정비 날짜
    @Column(name = "dateOfMaintenance", nullable = false)
    private LocalDate dateOfMaintenance;

    //정비 위치
    @Column(name = "location", nullable = false)
    private String location;

    // 정비 위치 리스트?
//    @Column(name = "location")
//    private List<> location = new ArrayList<>();

    //정비 가격
    @Column(name = "amount", nullable = false)
    private String amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id")
    private BikeEntity bike;

    // TODO : 사진 기록해야함 -> 멀티파트를 이용한 썸네일 저장
    // TODO : BLOB같은거 이용해서 내용에 사진도 들어갈 수 있게 저장

    // 상세내용
    @Column(name="content")
    private String content;

    public static MaintenanceEntity createMaintenance(MaintenanceInsertRequest request){

        return MaintenanceEntity.builder()
                .title(request.getTitle())
                .area(request.getArea())
                .dateOfMaintenance(request.getDateOfMaintenance())
                .location(request.getLocation())
                .amount(request.getAmount())
                .content(request.getContent())
                .build();
    }

    public void updateMaintenance(MaintenanceUpdateRequest request){
        this.title = request.getTitle();
        this.area = request.getArea();
        this.dateOfMaintenance = request.getDateOfMaintenance();
        this.location = request.getLocation();
        this.amount = request.getAmount();

    }

}
