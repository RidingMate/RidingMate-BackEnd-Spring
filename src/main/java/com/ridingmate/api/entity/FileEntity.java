package com.ridingmate.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

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
        TODO : 리사이즈 파일 같은엔티티로 통용하고 경로만 바꾸는건 어떨지
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
