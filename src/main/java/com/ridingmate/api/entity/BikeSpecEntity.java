package com.ridingmate.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class BikeSpecEntity {

    /*
        모든 바이크의 정보를 저장하는 Table
        TODO : 어떤 정보 뽑아서 사용할 수 있는지 확인해야함

        이전에 활용했던 데이터 :
            가격
            배기 : cc로 통일
            출력
            토크
            기름 용량
            엔진
            중량
            시트고
            ->데이터 베이스 다시 구매할 수 있는지 확인해야함
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;


}
