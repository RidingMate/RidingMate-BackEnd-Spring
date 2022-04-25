package com.ridingmate.api.entity;

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
@Table(name = "RMC_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class BoardEntity extends BaseTime{

    /*
        중고거래 게시글을 작성할 테이블
        TODO : 상태는 Enum으로 변경해야함
            판매중, 예약중, 거래완료 -> 패키지 위치 논의

        comment와 연관관계 필요

        TODO : 중고거래글, 공지사항을 나눌 Enum 필요정

        TODO : 내 바이크를 올릴경우 내 바이크와 연관관계 필요

        TODO : 직거래를 위한 지역에 대한 연관관계도 필요할듯
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    //제목
    @Column(name = "title")
    private String title;

    //조회수
    @Column(name = "hit")
    private int hit;

    //제조사
    @Column(name = "company")
    private String company;

    //모델명
    @Column(name = "model_name")
    private String model_name;

    //연비
    @Column(name = "fuel_efficiency")
    private double fuel_efficiency;

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

    // 댓글
    @OneToMany(mappedBy = "board")
    private List<CommentEntity> comments = new ArrayList<>();

    //TODO : 썸네일 저장을 위한 location 저장 컬럼
    //        File에 대한 entity 필요할거같음 생성해서 연관관계 연결
    //TODO : 내용과 사진을 같이 저장할 BLOB와 같은 컬럼
    //TODO : Enum 상태를 저장할 컬럼



}
