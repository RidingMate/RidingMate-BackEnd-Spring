package com.ridingmate.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "RMC_FILE")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends BaseTime {

    /*
        썸네일 등의 파일 저장 테이블

        이미지 저장 경로
        이미지 표출 방법 - cloudFront 사용할지
        리사이즈 저장이 필요한지.
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    //파일 코드 생성
    //TODO : 형식 정해야 할듯
    @Column(name = "file_code")
    private String fileCode;

    //원본 파일 이름
    @Column(name = "original_name")
    @JsonIgnore
    private String originalName;

    //저장 파일 이름
    @Column(name = "stored_name")
    @JsonIgnore
    private String storedName;

    //저장 경로
    @Column(name = "folder_location")
    private String folderLocation;

    //경로
    @Column(name = "location")
    @JsonIgnore
    private String location;

    //확장자
    @Column(name = "file_ext")
    @JsonIgnore
    private String fileExt;

    //파일 사이즈
    @Column(name = "file_size")
    @JsonIgnore
    private long fileSize;
}
