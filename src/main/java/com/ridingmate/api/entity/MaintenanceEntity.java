package com.ridingmate.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "RMC_MAINTENANCE")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class MaintenanceEntity extends BaseTime{

    /*
        정비 기록를 저장할 Table
        나의 바이크에서 연관관계로 설정
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    //정비 위치
    @Column(name = "area")
    private String area;

    //정비 가격
    @Column(name = "amount")
    private String amount;

    //정비 위치
    @Column(name = "location")
    private String location;

    // TODO : 사진 기록해야함 -> 멀티파트를 이용한 썸네일 저장
    // TODO : BLOB같은거 이용해서 내용에 사진도 들어갈 수 있게 저장

}
