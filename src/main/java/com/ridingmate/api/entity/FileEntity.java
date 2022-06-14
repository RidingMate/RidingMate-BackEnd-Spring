package com.ridingmate.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ridingmate.api.payload.common.FileResult;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public FileEntity createEntity(FileResult fileResult){
        return FileEntity.builder()
                .originalName(fileResult.getOriginalFileName())
                .location(fileResult.getUrl())
                .build();
    }

    public void connectBike(BikeEntity bikeEntity){
        this.bike = bikeEntity;
    }
}
