package com.ridingmate.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ridingmate.api.payload.common.FileResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RMC_FILE")
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends BaseTime {

    /*
        썸네일 등의 파일 저장 테이블

        이미지 저장 경로
        이미지 표출 방법 - cloudFront 사용할지
        리사이즈 저장이 필요한지.


        유저 - 프로필
        바이크 - 바이크 사진
        게실글 - 게시글 사진
        정비 - 정비 사진

     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    //원본 파일 이름
    @Column(name = "original_name")
    @JsonIgnore
    private String originalName;

    //경로
    @Column(name = "location")
    @JsonIgnore
    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id")
    private BikeEntity bike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id")
    private MaintenanceEntity maintenance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public FileEntity createEntity(FileResult fileResult){
        return builder()
                .originalName(fileResult.getOriginalFileName())
                .location(fileResult.getUrl())
                .build();
    }

    public void connectBike(BikeEntity bikeEntity){
        bike = bikeEntity;
    }

    public void connectBoard(BoardEntity board) {
        this.board = board;
    }

    public void connectMaintenance(MaintenanceEntity maintenance) {this.maintenance = maintenance;}

    public void connectUser(UserEntity user) {
        this.user = user;
    }
}
